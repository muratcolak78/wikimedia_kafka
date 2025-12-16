package com.wikimedia.wikimedia_kafka_producer;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/wikimedia")
@CrossOrigin(origins = "*")
public class WikimediaController {
    // injection of WikimediaProducer
    private WikimediaChangeProducer producer;

    public WikimediaController(WikimediaChangeProducer producer) {
        this.producer = producer;
    }

    //get changes from wikimedia with seconds
    @GetMapping("/getChanges")
    public ResponseEntity<String> getChanges(@RequestParam("second") int seconds){

        if (seconds <= 0) {
            return ResponseEntity.badRequest().body("seconds must be > 0");
        }

        producer.start(seconds);
        return ResponseEntity.ok("Started streaming for " + seconds + " seconds");

    }

}
