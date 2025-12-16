package com.wikimedia.wikimedia_kafka_producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WikimediaChangeProducer {

    //to save lod data we are using Logger from slf4j
    private static final Logger LOGGER= LoggerFactory.getLogger(WikimediaChangeProducer.class);

    //we are using kafkatemplate to sende message to Kafka topic

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final WebClient webClient;



    public WikimediaChangeProducer(KafkaTemplate<String, String> kafkaTemplate, WebClient webClient) {
        this.kafkaTemplate = kafkaTemplate;
        this.webClient=webClient;

    }


    // to read changes at the real time, we are using webflux,
    public void start(int seconds){

        AtomicInteger counter=new AtomicInteger(0);

        webClient.get()
                .uri("https://stream.wikimedia.org/v2/stream/recentchange")
                .retrieve()
                .bodyToFlux(String.class)
                .take(Duration.ofSeconds(seconds))
                .doOnNext(event -> {
                    LOGGER.info("Message sent -> {}", event);
                    kafkaTemplate.send("wikimedia_change", event);

                    counter.incrementAndGet();

                })

                .doOnComplete(()->{
                    LOGGER.info("Total changes number: {}", counter.get());

                })
                .subscribe();

    }
}
