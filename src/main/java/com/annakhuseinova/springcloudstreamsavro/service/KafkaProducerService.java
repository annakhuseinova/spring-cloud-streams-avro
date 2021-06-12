package com.annakhuseinova.springcloudstreamsavro.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String key, String value){
        log.debug(String.format("Producing message - K: %s, Value: %s to topic: %s", key, value, topic));
        kafkaTemplate.send(topic, key, value);
    }
}
