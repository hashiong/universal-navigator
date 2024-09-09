package com.hashiong.universal_navigator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WaitTimeScheduler {

    @Autowired
    private QueueTimesService queueTimesService;

    @Scheduled(fixedRate = 120000) // Fetch every 2 min
    public void scheduleFetchAndStoreWaitTimes() {
        
        queueTimesService.fetchAndStoreRides("66");
    }
}
