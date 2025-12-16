package com.wikimedia.wikimedia_kafka_consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecentChangeEvent {

    @JsonProperty("$schema")
    private String schema;

    private Meta meta;
    private long id;
    private String type;
    private int namespace;
    private String title;

    @JsonProperty("title_url")
    private String titleUrl;

    private String comment;
    private long timestamp;
    private String user;
    private boolean bot;

    @JsonProperty("notify_url")
    private String notifyUrl;

    @JsonProperty("server_url")
    private String serverUrl;

    @JsonProperty("server_name")
    private String serverName;

    @JsonProperty("server_script_path")
    private String serverScriptPath;

    private String wiki;

    private String parsedcomment;

}
