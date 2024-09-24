package com.hashiong.universal_navigator.repository;

import com.hashiong.universal_navigator.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {
}
