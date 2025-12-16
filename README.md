# Wikimedia Kafka Streaming Project

![2025-12-1611-43-08-ezgif com-video-to-gif-converter](https://github.com/user-attachments/assets/e743cf5f-d23a-4889-a9f9-219b170f8dee)


Dieses Projekt demonstriert eine einfache, ereignisgesteuerte Microservice-Architektur mit **Spring Boot**, **Apache Kafka**, **WebFlux** und **Server-Sent Events (SSE)**.

Das Ziel ist es, **Echtzeit-Änderungen von Wikimedia** zu erfassen, über Kafka zu verteilen, in einer Datenbank zu speichern und gleichzeitig live im Frontend anzuzeigen.

---

## Projektübersicht

Wikimedia stellt einen öffentlichen Streaming-Endpunkt zur Verfügung, über den aktuelle Änderungen in Echtzeit empfangen werden können.

Dieses Projekt besteht aus zwei voneinander getrennten Microservices:

### 1. wikimedia_kafka_producer
- Baut eine Verbindung zum Wikimedia-Streaming-Endpunkt auf
- Verwendet **Spring WebFlux**, um die Daten reaktiv zu verarbeiten
- Liest Änderungen für eine konfigurierbare Dauer (z. B. 5 oder 10 Sekunden)
- Sendet jedes empfangene Event an ein **Kafka Topic**

### 2. wikimedia_kafka_consumer
- Abonniert das Kafka Topic
- Liest die eingehenden Nachrichten
- Wandelt die JSON-Daten in ein **Java-Objekt** um
- Speichert die Daten in einer **SQLite-Datenbank**
- Leitet die Events zusätzlich über **Server-Sent Events (SSE)** an ein Frontend weiter

---

## Frontend

Das Frontend ist bewusst einfach gehalten (HTML + JavaScript) und dient zur Visualisierung des gesamten Prozesses.

Der Benutzer kann zwei Parameter steuern:

1. **Dauer (Sekunden)**  
   - Gibt an, wie lange der Wikimedia-Stream aktiv sein soll  
   - Standardmäßig bis zu 10 Sekunden (erweiterbar)

2. **Anzuzeigendes Feld**  
   - Zum Beispiel: Title, User, Comment oder Parsed Comment  
   - Der Benutzer wählt, welche Information live angezeigt wird

Nach Klick auf **Start**:
- Der Producer beginnt mit dem Streaming
- Die Daten werden an Kafka gesendet
- Der Consumer verarbeitet und speichert die Daten
- Die ausgewählten Informationen erscheinen live im Frontend

---

## Voraussetzungen

- Java 17 oder höher
- Apache Kafka **3.4.0 (Scala 2.13)**
- Windows (PowerShell)
- Maven

---

## Installation und Start (Windows)

### 1. Kafka herunterladen
Lade **Apache Kafka 3.4.0 (Scala 2.13)** von der offiziellen Kafka-Webseite herunter und entpacke das Archiv in ein Verzeichnis deiner Wahl, z. B.:


Diese Version arbeitet stabil mit **Zookeeper** und die Windows-Batch-Dateien funktionieren zuverlässig.

---

### 2. Zookeeper starten
Öffne eine neue **PowerShell** im Kafka-Verzeichnis und führe folgenden Befehl aus:

```powershell
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
Zookeeper muss während der gesamten Laufzeit aktiv bleiben.

3. Kafka Broker starten

Öffne eine zweite PowerShell im gleichen Kafka-Verzeichnis und starte den Kafka Broker mit:

.\bin\windows\kafka-server-start.bat .\config\server.properties

4. Microservices starten

Starte anschließend beide Spring Boot Anwendungen:

wikimedia_kafka_consumer

wikimedia_kafka_producer

Die Services können über eine IDE (z. B. IntelliJ IDEA) oder über Maven gestartet werden:

mvn spring-boot:run

5. Frontend öffnen

Öffne im Browser die folgende Datei aus dem Producer-Projekt:

wikimedia_kafka_producer/src/main/resources/static/index.html


Über das Frontend kann der Streaming-Prozess gesteuert und live beobachtet werden.

Verwendete Technologien

Java 17+

Spring Boot

Spring WebFlux

Apache Kafka

Server-Sent Events (SSE)

SQLite

HTML / JavaScript

Ziel des Projekts

Dieses Projekt dient als Lern- und Demonstrationsprojekt, um folgende Konzepte praktisch umzusetzen:

Microservice-Architektur

Event-Driven Architecture mit Apache Kafka

Reaktive Programmierung mit WebFlux

Asynchrone Datenverarbeitung

Echtzeit-Datenübertragung mit Server-Sent Events

Das Projekt zeigt, wie Echtzeitdaten zuverlässig verarbeitet, gespeichert und visualisiert werden können.
