package com.wikimedia.wikimedia_kafka_consumer;

import com.wikimedia.wikimedia_kafka_consumer.model.RecentChangeEvent;
import com.wikimedia.wikimedia_kafka_consumer.model.WikiDataObject;
import com.wikimedia.wikimedia_kafka_consumer.repository.WikiRapository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class Consumer {

    private static final Logger LOGGER= LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private final KafkaTemplate<String , String> template;

    @Autowired
    private final WikiRapository repository;

    private final ObjectMapper mapper;

    private final Sinks.Many<RecentChangeEvent> sink =
            Sinks.many().multicast().onBackpressureBuffer();

    private static String variable ="";


    public Consumer(KafkaTemplate<String , String> template, WikiRapository repository, ObjectMapper mapper){
        this.template=template;
        this.repository=repository;
        this.mapper=mapper;
    }

    public Flux<String> streamToFrontend(String variable) {
        return sink.asFlux().map(ev -> pick(ev, variable));
    }

    private String pick(RecentChangeEvent ev, String variable) {
        if (ev == null) return "null";

        return switch (variable) {
            case "Title" -> safe(ev.getTitle());
            case "Comment" -> safe(ev.getComment());
            case "User" -> safe(ev.getUser());
            case "Parsed Comment" -> safe(ev.getParsedcomment());
            default -> safe(ev.getTitle());
        };
    }
    private String safe(String s) {
        return s == null ? "" : s;
    }


    @KafkaListener(topics = "wikimedia_change", groupId = "mygroup" )
    public void listener(String data){
        try {

            RecentChangeEvent event = mapper.readValue(data, RecentChangeEvent.class);
            WikiDataObject object=new WikiDataObject();
            object.setData(data);
            //repository.saveData(object);

            repository.saveEvent(event);
            sink.tryEmitNext(event);
            LOGGER.info(String.format("Message received -> %s", data));

        }catch (JsonParseException e){

            LOGGER.warn("JSON parse failed. Sending to error topic. payload={}",e);
            template.send("wikimedia_change_parse_error", data);

        }catch (Exception e) {

            //
            LOGGER.error("Processing failed. Sending to retry/error topic. payload={}", e);
            template.send("wikimedia_change_processing_error", data);
        }


    }

}
