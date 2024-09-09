package com.hashiong.universal_navigator.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ride")
public class Ride {

    @Id
    @Column(name = "ride_id")
    private Integer id;

    @Column(name = "ride_name", nullable = false)
    private String rideName;

    // Cache for the latest status info
    @Column(name = "last_wait_time")
    private Integer lastWaitTime;

    @Column(name = "last_is_open")
    private Boolean lastIsOpen;

    @Column(name = "last_status_update")
    private LocalDateTime lastStatusUpdate;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRideName() {
        return rideName;
    }

    public void setRideName(String rideName) {
        this.rideName = rideName;
    }

    public Integer getLastWaitTime() {
        return lastWaitTime;
    }

    public void setLastWaitTime(Integer lastWaitTime) {
        this.lastWaitTime = lastWaitTime;
    }

    public Boolean getLastIsOpen() {
        return lastIsOpen;
    }

    public void setLastIsOpen(Boolean lastIsOpen) {
        this.lastIsOpen = lastIsOpen;
    }

    public LocalDateTime getLastStatusUpdate() {
        return lastStatusUpdate;
    }

    public void setLastStatusUpdate(LocalDateTime lastStatusUpdate) {
        this.lastStatusUpdate = lastStatusUpdate;
    }
}
