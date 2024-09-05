package com.hashiong.universal_navigator.repository;

import com.hashiong.universal_navigator.model.WaitTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WaitTimeRepository extends JpaRepository<WaitTime, Long> {

    // Find wait times for a specific ride
    List<WaitTime> findByRideId(String rideId);

    // Find wait times between two timestamps (e.g., for historical data queries)
    List<WaitTime> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
