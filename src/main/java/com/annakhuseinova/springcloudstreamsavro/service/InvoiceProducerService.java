package com.annakhuseinova.springcloudstreamsavro.service;


import com.annakhuseinova.springcloudstreamsaavro.model.PosInvoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceProducerService {

    @Value("${application.configs.topic.name}")
    private String TOPIC_NAME;
    private final KafkaTemplate<String, PosInvoice> kafkaTemplate;

    public void sendMessage(PosInvoice posInvoice){
        log.debug(String.format("Producing Invoice No: %s", posInvoice.getInvoiceNumber()));
        kafkaTemplate.send(TOPIC_NAME, posInvoice.getStoreID(), posInvoice);
    }
}
