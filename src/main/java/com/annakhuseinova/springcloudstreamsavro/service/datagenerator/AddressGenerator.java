package com.annakhuseinova.springcloudstreamsavro.service.datagenerator;

import com.annakhuseinova.springcloudstreamsaavro.model.DeliveryAddress;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Random;

@Service
public class AddressGenerator {

    private final Random random;
    private final DeliveryAddress[] addresses;

    public AddressGenerator(){
        final String DATAFILE = "src/main/resources/data/address.json";
        final ObjectMapper mapper = new ObjectMapper();
        try {
            addresses = mapper.readValue(new File(DATAFILE), DeliveryAddress[].class);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        random = new Random();
    }

    private int getIndex(){
        return random.nextInt(100);
    }

    public DeliveryAddress getRandomAddress(){
        return addresses[getIndex()];
    }
}
