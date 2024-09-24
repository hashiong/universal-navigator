package com.hashiong.universal_navigator.repository;

import com.hashiong.universal_navigator.model.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideStatusRepository extends JpaRepository<RideStatus, Integer> {

}
