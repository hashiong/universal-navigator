package com.hashiong.universal_navigator.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ride_status", indexes = {
    @Index(name = "idx_ride_last_updated", columnList = "ride_id, last_updated")
})
public class RideStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ridestatus_id")
    private Integer statusId;

    @Column(name = "ride_id", nullable = false)
    private Integer rideId;

    @Column(name = "is_open", nullable = false)
    private Boolean isOpen;

    @Column(name = "wait_time")
    private Integer waitTime;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Getters and Setters
    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getRideId() {
        return rideId;
    }

    public void setRideId(Integer rideId) {
        this.rideId = rideId;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
