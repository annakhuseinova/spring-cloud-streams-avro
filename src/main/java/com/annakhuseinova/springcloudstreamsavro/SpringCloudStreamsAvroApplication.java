package com.annakhuseinova.springcloudstreamsavro;

import com.annakhuseinova.springcloudstreamsavro.service.InvoiceProducerService;
import com.annakhuseinova.springcloudstreamsavro.service.datagenerator.InvoiceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringCloudStreamsAvroApplication implements ApplicationRunner {

	private final InvoiceProducerService producerService;
	private final InvoiceGenerator invoiceGenerator;
	@Value("${application.configs.invoice.count}")
	private int INVOICE_COUNT;

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamsAvroApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		for (int i=0; i < INVOICE_COUNT; i++){
			producerService.sendMessage(invoiceGenerator.getRandomInvoice());
			Thread.sleep(1000);
		}
	}
}
