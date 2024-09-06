package com.hashiong.universal_navigator.controller;

import com.hashiong.universal_navigator.model.Ride;
import com.hashiong.universal_navigator.service.QueueTimesService;
import com.hashiong.universal_navigator.repository.RideRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final QueueTimesService queueTimesService;
    private final RideRepository rideRepository;

    // Constructor Injection
    public RideController(QueueTimesService queueTimesService, RideRepository rideRepository) {
        this.queueTimesService = queueTimesService;
        this.rideRepository = rideRepository;
    }

    // Endpoint to fetch and store ride data from the external API
    @GetMapping("/fetch")
    public String fetchAndStoreRides(@RequestParam("parkId") String parkId) {
        queueTimesService.fetchAndStoreRides(parkId);
        return "Ride data fetched and stored successfully.";
    }

    // Endpoint to list all rides from the database
    @GetMapping("/list")
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }
}
