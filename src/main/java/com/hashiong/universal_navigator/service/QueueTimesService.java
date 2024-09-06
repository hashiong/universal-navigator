package com.hashiong.universal_navigator.service;

import com.hashiong.universal_navigator.model.Ride;
import com.hashiong.universal_navigator.model.RideStatus;
import com.hashiong.universal_navigator.repository.RideRepository;
import com.hashiong.universal_navigator.repository.RideStatusRepository;
import com.hashiong.universal_navigator.service.dto.QueueTimesResponseDTO;
import com.hashiong.universal_navigator.service.dto.RideDTO;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;

@Service
public class QueueTimesService {

    private static final String QUEUE_TIMES_API_URL = "https://queue-times.com/parks/{park_id}/queue_times.json";

    private final RestTemplate restTemplate;
    private final RideRepository rideRepository;
    private final RideStatusRepository rideStatusRepository;
    private static final Logger logger = LoggerFactory.getLogger(QueueTimesService.class);

    public QueueTimesService(RestTemplateBuilder restTemplateBuilder, RideRepository rideRepository, RideStatusRepository rideStatusRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.rideRepository = rideRepository;
        this.rideStatusRepository = rideStatusRepository;
    }

    @Transactional
    public void fetchAndStoreRides(String parkId) {
        logger.info("Fetching ride data for park ID: {}", parkId);
        String url = QUEUE_TIMES_API_URL.replace("{park_id}", parkId);

        // Deserialize to QueueTimesResponseDTO
        QueueTimesResponseDTO response = restTemplate.getForObject(url, QueueTimesResponseDTO.class);

        if (response != null && response.getRides() != null) {
            response.getRides().forEach(this::storeOrUpdateRideAndStatus);
        }
        logger.info("Finished fetching and storing rides for park ID: {}", parkId);
    }

    // Store or update the Ride and its statuses
    private void storeOrUpdateRideAndStatus(RideDTO rideDTO) {
        logger.info("Processing ride: {}", rideDTO.getName());

        // Check if the ride exists in the database
        Optional<Ride> existingRide = rideRepository.findById(rideDTO.getId());
        Ride ride;

        if (existingRide.isPresent()) {
            ride = existingRide.get();
        } else {
            ride = new Ride();
            ride.setId(rideDTO.getId());
            ride.setRideName(rideDTO.getName());
        }

        String lastUpdated_str = rideDTO.getLastUpdated();
        LocalDateTime lastUpdated = convertToLocalDateTime(lastUpdated_str);

        // Check if the last status update is the same as the current one
        if (ride.getLastStatusUpdate() == null || !ride.getLastStatusUpdate().equals(lastUpdated)) {
            // Update Ride with the latest status
            ride.setLastWaitTime(rideDTO.getWaitTime());
            ride.setLastIsOpen(rideDTO.getIsOpen());
            ride.setLastStatusUpdate(lastUpdated);
            rideRepository.save(ride);  // Save the updated ride information

            // Also, store a new status record in RideStatus
            RideStatus rideStatus = new RideStatus();
            rideStatus.setRideId(ride.getId());
            rideStatus.setIsOpen(rideDTO.getIsOpen());
            rideStatus.setWaitTime(rideDTO.getWaitTime());
            rideStatus.setLastUpdated(lastUpdated);
            rideStatusRepository.save(rideStatus);
        }
    }

    private LocalDateTime convertToLocalDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.isEmpty()) {
            throw new IllegalArgumentException("DateTime string cannot be null or empty");
        }

        // Parse the timestamp with OffsetDateTime and convert to LocalDateTime
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeString);
        return offsetDateTime.toLocalDateTime();
    }
}
