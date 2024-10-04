package com.hashiong.universal_navigator.controller;

import com.hashiong.universal_navigator.model.Ride;
import com.hashiong.universal_navigator.model.Location;
import com.hashiong.universal_navigator.service.QueueTimesService;
import com.hashiong.universal_navigator.service.RouteService;
import com.hashiong.universal_navigator.repository.RideRepository;
import com.hashiong.universal_navigator.service.dto.RouteRequestDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from localhost:3000
public class RideController {

    private final QueueTimesService queueTimesService;
    private final RideRepository rideRepository;
    private final RouteService routeService;

    // Constructor Injection
    public RideController(QueueTimesService queueTimesService, RideRepository rideRepository, RouteService routeService) {
        this.queueTimesService = queueTimesService;
        this.rideRepository = rideRepository;
        this.routeService = routeService;
    }

    // Endpoint to fetch and store ride data from the external API
    @GetMapping("/fetch")
    public String fetchAndStoreRides(@RequestParam("parkId") String parkId) {
        queueTimesService.fetchAndStoreRides(parkId);
        return "Ride data fetched and stored successfully.";
    }

    @PostMapping("/optimal-route")
    public ResponseEntity<?> getOptimalRoute(@RequestBody RouteRequestDTO routeRequest) {
        System.out.println("route request: " + routeRequest.getRideIds());
        // Validate input
        if (routeRequest.getRideIds() == null || routeRequest.getRideIds().isEmpty()) {
            return ResponseEntity.badRequest().body("Ride IDs cannot be null or empty.");
        }
        if (routeRequest.getWaitWeight() < 0 || routeRequest.getWalkWeight() < 0) {
            return ResponseEntity.badRequest().body("Weights must be non-negative.");
        }

        try {
            List<Location> optimalRoute = routeService.findOptimalRoute(
                routeRequest.getRideIds(),
                routeRequest.getWaitWeight(),
                routeRequest.getWalkWeight()
            );
            return ResponseEntity.ok(optimalRoute);
        } catch (Exception e) {
            // Log the error (you can use a logger instead of printing the stack trace)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("An error occurred while calculating the optimal route.");
        }
    }




    // Endpoint to list all rides from the database
    @GetMapping("/list")
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }
}
