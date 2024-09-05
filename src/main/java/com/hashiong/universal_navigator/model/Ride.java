package com.hashiong.universal_navigator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "rides")  // Specifies the table name in the database
public class Ride {

    @Id
    private Long id;  // Maps to the 'id' field in your API

    @Column(name = "name", nullable = false)  // Ride name must not be null
    private String name;

    @Column(name = "is_open")  // Indicates if the ride is currently open
    private boolean is_open;

    @Column(name = "wait_time")  // Current wait time for the ride
    private int wait_time;

    @Column(name = "last_updated")  // The timestamp of the last update
    private LocalDateTime last_updated;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public int getWait_time() {
        return wait_time;
    }

    public void setWait_time(int wait_time) {
        this.wait_time = wait_time;
    }

    public LocalDateTime getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(LocalDateTime last_updated) {
        this.last_updated = last_updated;
    }
}
