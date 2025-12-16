package com.wikimedia.wikimedia_kafka_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WikimediaKafkaProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WikimediaKafkaProducerApplication.class, args);
	}

	@Autowired
	private WikimediaChangeProducer producer;


	@Override
	public void run(String... args) throws Exception {
		//producer.start(5);
	}
}
