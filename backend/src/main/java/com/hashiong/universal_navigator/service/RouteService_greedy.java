// package com.hashiong.universal_navigator.service;

// import com.hashiong.universal_navigator.model.Ride;
// import com.hashiong.universal_navigator.model.Location;
// import com.hashiong.universal_navigator.repository.LocationRepository;
// import com.hashiong.universal_navigator.repository.RideRepository;

// import org.springframework.stereotype.Service;

// import java.util.*;

// @Service
// public class RouteService_greedy {

//     private final LocationRepository locationRepository;
//     private final RideRepository rideRepository;

//     public RouteService(LocationRepository locationRepository, RideRepository rideRepository) {
//         this.locationRepository = locationRepository;
//         this.rideRepository = rideRepository;
//     }

//     public List<Location> findOptimalRoute(List<Integer> rideIds, double waitWeight, double walkWeight) {

//         // Validate weights
//         if (waitWeight <= 0 && walkWeight <= 0) {
//             throw new IllegalArgumentException("At least one of the weights must be positive.");
//         }
    
//         // Map ride IDs to their Locations
//         Map<Integer, Location> rideLocations = new HashMap<>();
//         for (Integer rideId : rideIds) {
//             Optional<Location> optionalLocation = locationRepository.findById(rideId);
//             if (optionalLocation.isPresent()) {
//                 rideLocations.put(rideId, optionalLocation.get());
//             } else {
//                 throw new IllegalArgumentException("Location not found for ride ID: " + rideId);
//             }
//         }
    
//         // Starting point (Main Entrance)
//         Optional<Location> optionalStart = locationRepository.findById(-1);
//         if (!optionalStart.isPresent()) {
//             throw new IllegalArgumentException("Starting location not found");
//         }
//         Location currentLocation = optionalStart.get();
    
//         List<Location> route = new ArrayList<>();
//         route.add(currentLocation);
    
//         Set<Integer> remainingRideIds = new HashSet<>(rideIds);
    
//         while (!remainingRideIds.isEmpty()) {
//             Location nextLocation = null;
//             double minCost = Double.MAX_VALUE;
    
//             for (Integer rideId : remainingRideIds) {
//                 Location potentialLocation = rideLocations.get(rideId);
    
//                 double distance = calculateDistance(currentLocation.getLatitude(), currentLocation.getLongitude(),
//                         potentialLocation.getLatitude(), potentialLocation.getLongitude());
//                 double walkingTime = distance / 1.4 / 60; // meters to minutes
    
//                 Optional<Ride> optionalRide = rideRepository.findById(rideId);
//                 if (!optionalRide.isPresent()) continue;
    
//                 Ride rideData = optionalRide.get();
//                 double waitTime = rideData.getLastWaitTime();
    
//                 double cost = heuristic(walkingTime, waitTime, waitWeight, walkWeight);
    
//                 if (cost < minCost) {
//                     minCost = cost;
//                     nextLocation = potentialLocation;
//                 }
//             }
    
//             if (nextLocation != null) {
//                 route.add(nextLocation);
//                 currentLocation = nextLocation;
//                 remainingRideIds.remove(nextLocation.getId());
//                 System.out.println("Added to route: " + nextLocation.getId());
//             } else {
//                 // No accessible rides left
//                 break;
//             }
//         }
    
//         return route;
//     }
    

//     // Heuristic cost function based on time (wait + walking time)
//     private double heuristic(double walkingTime, double waitTime, double waitWeight, double walkWeight) {
//         double totalWeight = waitWeight + walkWeight;
//         if (totalWeight == 0) {
//             throw new IllegalArgumentException("Total weight cannot be zero.");
//         }

//         double normalizedWaitWeight = waitWeight / totalWeight;
//         double normalizedWalkWeight = walkWeight / totalWeight;
//         return (normalizedWalkWeight * walkingTime) + (normalizedWaitWeight * waitTime);
//     }

//     private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
//         // Calculate the distance in meters using the Haversine formula
//         final int EARTH_RADIUS = 6371000; // in meters
//         double dLat = Math.toRadians(lat2 - lat1);
//         double dLon = Math.toRadians(lon2 - lon1);
//         double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                 Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                 Math.sin(dLon / 2) * Math.sin(dLon / 2);
//         double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//         return EARTH_RADIUS * c;
//     }

//     // Inner class to store node information in the priority queue
//     private class Node {
//         Location location;
//         double cost;

//         Node(Location location, double cost) {
//             this.location = location;
//             this.cost = cost;
//         }
//     }
// }
