package com.wikimedia.wikimedia_kafka_consumer.repository;

import com.wikimedia.wikimedia_kafka_consumer.model.RecentChangeEvent;
import com.wikimedia.wikimedia_kafka_consumer.model.WikiDataObject;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@Repository
public class WikiRapository {
    private static final String DB_URL = "jdbc:sqlite:database.db";

    public void saveData(WikiDataObject object) {
        String sql = "INSERT INTO wikimedia_events (data) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, object.getData());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Database error while saving raw wikimedia data", e);
        }
    }

    public void saveEvent(RecentChangeEvent event) {

        String sql = "INSERT INTO wikimedia_events2 (" +
                "schema, meta_uri, meta_request_id, meta_id, meta_domain, meta_stream, meta_dt, meta_topic, meta_partition, meta_offset, " +
                "event_id, type, namespace, title, title_url, comment, timestamp, user, bot, notify_url, server_url, server_name, server_script_path, wiki, parsedcomment" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, event.getSchema());

            stmt.setString(2, event.getMeta().getUri());
            stmt.setString(3, event.getMeta().getRequestId());
            stmt.setString(4, event.getMeta().getId());
            stmt.setString(5, event.getMeta().getDomain());
            stmt.setString(6, event.getMeta().getStream());
            stmt.setString(7, event.getMeta().getDt());
            stmt.setString(8, event.getMeta().getTopic());
            stmt.setInt(9, event.getMeta().getPartition());
            stmt.setLong(10, event.getMeta().getOffset());

            stmt.setLong(11, event.getId());
            stmt.setString(12, event.getType());
            stmt.setInt(13, event.getNamespace());
            stmt.setString(14, event.getTitle());
            stmt.setString(15, event.getTitleUrl());
            stmt.setString(16, event.getComment());
            stmt.setLong(17, event.getTimestamp());
            stmt.setString(18, event.getUser());
            stmt.setBoolean(19, event.isBot());
            stmt.setString(20, event.getNotifyUrl());
            stmt.setString(21, event.getServerUrl());
            stmt.setString(22, event.getServerName());
            stmt.setString(23, event.getServerScriptPath());
            stmt.setString(24, event.getWiki());
            stmt.setString(25, event.getParsedcomment());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Database error while saving raw wikimedia data", e);
        }
    }
}
