package com.hashiong.universal_navigator.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RideDTO {
    private Integer id; // ride_id in the entity
    private String name;
    
    @JsonProperty("is_open") // This ensures proper mapping from the JSON field
    private Boolean isOpen;
    
    @JsonProperty("wait_time") // Ensures mapping for wait time
    private Integer waitTime;
    
    @JsonProperty("last_updated") // Mapping for the last updated field
    private String lastUpdated;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
