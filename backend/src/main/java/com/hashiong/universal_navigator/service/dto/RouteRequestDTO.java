package com.hashiong.universal_navigator.service.dto;
import java.util.List;

public class RouteRequestDTO {
    private List<Integer> rideIds;
    private double waitWeight;
    private double walkWeight;

    // Getters and Setters

    public List<Integer> getRideIds() {
        return rideIds;
    }

    public void setRideIds(List<Integer> rideIds) {
        this.rideIds = rideIds;
    }

    public double getWaitWeight() {
        return waitWeight;
    }

    public void setWaitWeight(double waitWeight) {
        this.waitWeight = waitWeight;
    }

    public double getWalkWeight() {
        return walkWeight;
    }

    public void setWalkWeight(double walkWeight) {
        this.walkWeight = walkWeight;
    }
}
