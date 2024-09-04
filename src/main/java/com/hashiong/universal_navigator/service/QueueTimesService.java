package com.hashiong.universal_navigator.service;

import com.hashiong.universal_navigator.model.WaitTime;
import com.hashiong.universal_navigator.repository.WaitTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestTemplateBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueTimesService {

    private static final String QUEUE_TIMES_API_URL = "https://queue-times.com/api/v1/parks/{park_id}/wait_times";

    private final RestTemplate restTemplate;
    private final WaitTimeRepository waitTimeRepository;

    @Autowired
    public QueueTimesService(RestTemplateBuilder restTemplateBuilder, WaitTimeRepository waitTimeRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.waitTimeRepository = waitTimeRepository;
    }

    public void fetchAndStoreWaitTimes(String parkId) {
        String url = QUEUE_TIMES_API_URL.replace("{park_id}", parkId);
        List<WaitTimeDTO> waitTimes = restTemplate.getForObject(url, List.class);

        List<WaitTime> waitTimeEntities = waitTimes.stream()
            .map(this::convertToEntity)
            .collect(Collectors.toList());

        waitTimeRepository.saveAll(waitTimeEntities);
    }

    private WaitTime convertToEntity(WaitTimeDTO dto) {
        WaitTime waitTime = new WaitTime();
        waitTime.setRideId(dto.getRideId());
        waitTime.setWaitTime(dto.getWaitTime());
        waitTime.setTimestamp(LocalDateTime.now());
        return waitTime;
    }
}
