package com.hashiong.universal_navigator.service;

import com.hashiong.universal_navigator.model.Ride;
import com.hashiong.universal_navigator.model.RideStatus;
import com.hashiong.universal_navigator.repository.RideRepository;
import com.hashiong.universal_navigator.repository.RideStatusRepository;
import com.hashiong.universal_navigator.service.dto.QueueTimesResponseDTO;
import com.hashiong.universal_navigator.service.dto.RideDTO;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class QueueTimesService {

    private static final String QUEUE_TIMES_API_URL = "https://queue-times.com/parks/{park_id}/queue_times.json";
    private static final Logger logger = LoggerFactory.getLogger(QueueTimesService.class);

    private final RestTemplate restTemplate;
    private final RideRepository rideRepository;
    private final RideStatusRepository rideStatusRepository;

    public QueueTimesService(RestTemplateBuilder restTemplateBuilder, RideRepository rideRepository, RideStatusRepository rideStatusRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.rideRepository = rideRepository;
        this.rideStatusRepository = rideStatusRepository;
    }

    public void fetchAndStoreRides(String parkId) {
        logger.info("Starting to fetch ride data for park ID: {}", parkId);
        String url = QUEUE_TIMES_API_URL.replace("{park_id}", parkId);

        QueueTimesResponseDTO response = restTemplate.getForObject(url, QueueTimesResponseDTO.class);

        if (response != null && response.getRides() != null) {
            logger.info("Fetched {} rides from the API", response.getRides().size());
            response.getRides().forEach(this::storeOrUpdateRideAndStatus);
        } else {
            logger.warn("No rides found for park ID: {}", parkId);
        }
        
        logger.info("Completed fetching and storing rides for park ID: {}", parkId);
    }

    private void storeOrUpdateRideAndStatus(RideDTO rideDTO) {
        logger.debug("Processing ride: {}", rideDTO.getName());

        // Check if the ride exists in the database
        Optional<Ride> existingRideOpt = rideRepository.findById(rideDTO.getId());
        Ride ride = existingRideOpt.orElseGet(() -> {
            logger.info("Creating new ride entry for: {}", rideDTO.getName());
            Ride newRide = new Ride();
            newRide.setId(rideDTO.getId());
            newRide.setRideName(rideDTO.getName());
            return newRide;
        });

        LocalDateTime lastUpdated = rideDTO.getLastUpdated();
        logger.info("Last Update time: {}", lastUpdated);
        logger.info("Last Update time in DB: {}", ride.getLastStatusUpdate());

        // Check if the ride status needs to be updated
        if (ride.getLastStatusUpdate() == null || !ride.getLastStatusUpdate().equals(lastUpdated)) {
            logger.debug("Updating status for ride: {}", ride.getRideName());

            // Update Ride with the latest status
            ride.setLastWaitTime(rideDTO.getWaitTime());
            ride.setLastIsOpen(rideDTO.getIsOpen());
            ride.setLastStatusUpdate(lastUpdated);
            rideRepository.save(ride);  // Save the updated ride information
            logger.info("Saved updated ride data for: {}", ride.getRideName());

            // Store a new status record in RideStatus
            RideStatus rideStatus = new RideStatus();
            rideStatus.setRideId(ride.getId());
            rideStatus.setIsOpen(rideDTO.getIsOpen());
            rideStatus.setWaitTime(rideDTO.getWaitTime());
            rideStatus.setLastUpdated(lastUpdated);
            rideStatusRepository.save(rideStatus);
            logger.info("Saved new ride status for: {}", ride.getRideName());
        } else {
            logger.info("No update needed for ride '{}'. The last update time is unchanged.", ride.getRideName());
        }
    }
}
