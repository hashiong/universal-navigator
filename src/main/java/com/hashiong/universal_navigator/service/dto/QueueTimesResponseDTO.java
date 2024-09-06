package com.hashiong.universal_navigator.service.dto;

import java.util.List;

public class QueueTimesResponseDTO {
    private List<RideDTO> rides;  // The list of rides

    public List<RideDTO> getRides() {
        return rides;
    }

    public void setRides(List<RideDTO> rides) {
        this.rides = rides;
    }
}
