package com.hashiong.universal_navigator.service;

import com.hashiong.universal_navigator.model.Ride;
import com.hashiong.universal_navigator.model.Location;
import com.hashiong.universal_navigator.repository.LocationRepository;
import com.hashiong.universal_navigator.repository.RideRepository;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteService {

    private final LocationRepository locationRepository;
    private final RideRepository rideRepository;

    public RouteService(LocationRepository locationRepository, RideRepository rideRepository) {
        this.locationRepository = locationRepository;
        this.rideRepository = rideRepository;
    }

    public List<Location> findOptimalRoute(List<Integer> rideIds, double waitWeight, double walkWeight) {

        // Validate weights
        if (waitWeight <= 0 && walkWeight <= 0) {
            throw new IllegalArgumentException("At least one of the weights must be positive.");
        }

        // Map ride IDs to their Locations
        Map<Integer, Location> rideLocations = new HashMap<>();
        for (Integer rideId : rideIds) {
            Optional<Location> optionalLocation = locationRepository.findById(rideId);
            if (optionalLocation.isPresent()) {
                rideLocations.put(rideId, optionalLocation.get());
            } else {
                throw new IllegalArgumentException("Location not found for ride ID: " + rideId);
            }
        }

        // Starting point (Main Entrance)
        Optional<Location> optionalStart = locationRepository.findById(-1);
        if (!optionalStart.isPresent()) {
            throw new IllegalArgumentException("Starting location not found");
        }
        Location startLocation = optionalStart.get();

        // Initialize data structures
        PriorityQueue<State> openSet = new PriorityQueue<>(Comparator.comparingDouble(a -> a.fScore));
        Map<State, Double> gScore = new HashMap<>();
        Map<State, State> cameFrom = new HashMap<>();

        // Initial state
        Set<Integer> initialRemainingRides = new HashSet<>(rideIds);
        State initialState = new State(startLocation, initialRemainingRides);
        gScore.put(initialState, 0.0);
        initialState.fScore = heuristic(startLocation, initialRemainingRides, rideLocations, waitWeight, walkWeight);
        openSet.add(initialState);

        while (!openSet.isEmpty()) {
            State currentState = openSet.poll();

            // Goal condition: all rides have been visited
            if (currentState.remainingRideIds.isEmpty()) {
                // Reconstruct path
                return reconstructPath(cameFrom, currentState);
            }

            // Generate successors
            for (Integer rideId : currentState.remainingRideIds) {
                Location nextLocation = rideLocations.get(rideId);

                double distance = calculateDistance(currentState.currentLocation.getLatitude(), currentState.currentLocation.getLongitude(),
                        nextLocation.getLatitude(), nextLocation.getLongitude());
                double walkingTime = distance / 1.4 / 60; // meters to minutes

                Optional<Ride> optionalRide = rideRepository.findById(rideId);
                if (!optionalRide.isPresent()) continue;

                Ride rideData = optionalRide.get();
                double waitTime = rideData.getLastWaitTime();

                // Calculate travel cost
                double travelCost = (waitWeight * waitTime) + (walkWeight * walkingTime);

                double tentativeGScore = gScore.get(currentState) + travelCost;

                // Create new state
                Set<Integer> newRemainingRides = new HashSet<>(currentState.remainingRideIds);
                newRemainingRides.remove(rideId);
                State successorState = new State(nextLocation, newRemainingRides);

                double existingGScore = gScore.getOrDefault(successorState, Double.MAX_VALUE);

                if (tentativeGScore < existingGScore) {
                    cameFrom.put(successorState, currentState);
                    gScore.put(successorState, tentativeGScore);

                    // Heuristic estimate
                    double hScore = heuristic(nextLocation, newRemainingRides, rideLocations, waitWeight, walkWeight);
                    successorState.fScore = tentativeGScore + hScore;

                    openSet.add(successorState);
                }
            }
        }

        // No path found
        return new ArrayList<>();
    }

    private double heuristic(Location currentLocation, Set<Integer> remainingRideIds,
                             Map<Integer, Location> rideLocations, double waitWeight, double walkWeight) {
        if (remainingRideIds.isEmpty()) return 0.0;

        // Estimate is minimal walking time to the remaining rides
        double minWalkingTime = Double.MAX_VALUE;

        for (Integer rideId : remainingRideIds) {
            Location rideLocation = rideLocations.get(rideId);
            double distance = calculateDistance(currentLocation.getLatitude(), currentLocation.getLongitude(),
                    rideLocation.getLatitude(), rideLocation.getLongitude());
            double walkingTime = distance / 1.4 / 60; // meters to minutes
            if (walkingTime < minWalkingTime) {
                minWalkingTime = walkingTime;
            }
        }

        // Multiply by weight
        return walkWeight * minWalkingTime;
    }

    private List<Location> reconstructPath(Map<State, State> cameFrom, State currentState) {
        List<Location> path = new ArrayList<>();
        path.add(currentState.currentLocation);

        while (cameFrom.containsKey(currentState)) {
            currentState = cameFrom.get(currentState);
            path.add(currentState.currentLocation);
        }
        Collections.reverse(path);
        return path;
    }

    // Calculate Haversine distance in meters
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371000; // in meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    // State class representing a node in the A* search
    private static class State {
        Location currentLocation;
        Set<Integer> remainingRideIds;
        double fScore;

        State(Location currentLocation, Set<Integer> remainingRideIds) {
            this.currentLocation = currentLocation;
            this.remainingRideIds = remainingRideIds;
            this.fScore = 0.0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(currentLocation.getId(), remainingRideIds);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof State)) return false;
            State other = (State) obj;
            return Objects.equals(currentLocation.getId(), other.currentLocation.getId())
                    && Objects.equals(remainingRideIds, other.remainingRideIds);
        }
    }
}
