package com.wikimedia.wikimedia_kafka_consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Meta {

    private String uri;

    @JsonProperty("request_id")
    private String requestId;

    private String id;
    private String domain;
    private String stream;
    private String dt;
    private String topic;
    private int partition;
    private long offset;

}
