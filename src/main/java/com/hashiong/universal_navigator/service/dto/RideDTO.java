package com.hashiong.universal_navigator.service.dto;

import java.time.LocalDateTime;

public class RideDTO {
    private Long id;
    private String name;
    private boolean is_open;
    private int wait_time;
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
