package com.wikimedia.wikimedia_kafka_consumer;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/wikimedia")
@CrossOrigin(origins = "*")
public class Controller {
    private final Consumer consumer;

    public Controller(Consumer consumer) {
        this.consumer = consumer;
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE )
    public Flux<String> stream(@RequestParam("variable") String variable) {
        return consumer.streamToFrontend(variable);
    }
}
