package com.annakhuseinova.springcloudstreamsavro.service.datagenerator;


import com.annakhuseinova.springcloudstreamsaavro.model.DeliveryAddress;
import com.annakhuseinova.springcloudstreamsaavro.model.LineItem;
import com.annakhuseinova.springcloudstreamsaavro.model.PosInvoice;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class InvoiceGenerator {
    private final Random invoiceIndex;
    private final Random invoiceNumber;
    private final Random numberOfItems;
    private final PosInvoice[] invoices;
    @Autowired
    private AddressGenerator addressGenerator;
    @Autowired
    private ProductGenerator productGenerator;

    public InvoiceGenerator() {
        String DATAFILE = "src/main/resources/data/Invoice.json";
        invoiceIndex = new Random();
        invoiceNumber = new Random();
        numberOfItems = new Random();
        ObjectMapper mapper;
        mapper = new ObjectMapper();
        try {
            invoices = mapper.readValue(new File(DATAFILE), PosInvoice[].class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getIndex() {
        return invoiceIndex.nextInt(100);
    }

    private int getNewInvoiceNumber() {
        return invoiceNumber.nextInt(99999999) + 99999;
    }

    private int getNoOfItems() {
        return numberOfItems.nextInt(4) + 1;
    }

    public PosInvoice getRandomInvoice() {
        PosInvoice invoice = invoices[getIndex()];
        invoice.setInvoiceNumber(Integer.toString(getNewInvoiceNumber()));
        invoice.setCreatedTime(System.currentTimeMillis());
        if ("HOME-DELIVERY".equalsIgnoreCase(invoice.getDeliveryType())) {
            DeliveryAddress deliveryAddress = addressGenerator.getRandomAddress();
            invoice.setDeliveryAddress(deliveryAddress);
        }
        int itemCount = getNoOfItems();
        double totalAmount = 0.0;
        List<LineItem> items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            LineItem item = productGenerator.getRandomProduct();
            totalAmount = totalAmount + item.getTotalValue();
            items.add(item);
        }
        invoice.setNumberOfItems(itemCount);
        invoice.setInvoiceLineItems(items);
        invoice.setTotalAmount(totalAmount);
        invoice.setTaxableAmount(totalAmount);
        invoice.setCGST(totalAmount * 0.025);
        invoice.setSGST(totalAmount * 0.025);
        invoice.setCESS(totalAmount * 0.00125);
        log.debug("Created invoice: {}", invoice);
        return invoice;
    }
}
