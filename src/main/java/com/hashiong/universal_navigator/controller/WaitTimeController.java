package com.hashiong.universal_navigator.controller;

import com.hashiong.universal_navigator.model.WaitTime;
import com.hashiong.universal_navigator.repository.WaitTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wait-times")
public class WaitTimeController {

    @Autowired
    private WaitTimeRepository waitTimeRepository;

    @GetMapping("/current")
    public ResponseEntity<List<WaitTime>> getCurrentWaitTimes() {
        List<WaitTime> waitTimes = waitTimeRepository.findAll();
        return ResponseEntity.ok(waitTimes);
    }
}
