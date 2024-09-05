package com.hashiong.universal_navigator.service;

import com.hashiong.universal_navigator.model.Ride;
import com.hashiong.universal_navigator.repository.RideRepository;
import com.hashiong.universal_navigator.service.dto.RideDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueTimesService {

    private static final String QUEUE_TIMES_API_URL = "https://queue-times.com/api/v1/parks/{park_id}/wait_times";

    private final RestTemplate restTemplate;
    private final RideRepository rideRepository;

    public QueueTimesService(RestTemplateBuilder restTemplateBuilder, RideRepository rideRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.rideRepository = rideRepository;
    }

    public void fetchAndStoreRides(String parkId) {
        String url = QUEUE_TIMES_API_URL.replace("{park_id}", parkId);
        RideDTO[] rideDTOs = restTemplate.getForObject(url, RideDTO[].class);

        List<Ride> rides = List.of(rideDTOs).stream()
            .map(this::convertToEntity)
            .collect(Collectors.toList());

        rideRepository.saveAll(rides);  // Save all rides to the database
    }

    private Ride convertToEntity(RideDTO dto) {
        Ride ride = new Ride();
        ride.setId(dto.getId());
        ride.setName(dto.getName());
        ride.setIs_open(dto.isIs_open());
        ride.setWait_time(dto.getWait_time());
        ride.setLast_updated(dto.getLast_updated());
        return ride;
    }
}
