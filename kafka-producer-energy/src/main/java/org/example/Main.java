package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Main {

  static Properties properties;

  static final ObjectMapper objectMapper = new ObjectMapper();

  private static void loadProperties() {
    properties = new Properties();
    try (InputStream input =
        Main.class.getClassLoader().getResourceAsStream("application.properties")) {
      if (input != null) {
        properties.load(input);
      } else {
        System.out.println("Configuration file not found");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static UUID getDeviceId() {
    return UUID.fromString(properties.getProperty("device-id"));
  }

  public static String getFileName() {
    return properties.getProperty("file-name");
  }
  public static String getTopicName() {
    return properties.getProperty("topic-name");
  }

  public static void main(String[] args) throws IOException, InterruptedException {

    loadProperties();
    UUID deviceId = getDeviceId();
    String csvFilePath = getFileName();
    String kafkaTopic = getTopicName();


    Properties properties = new Properties();
    properties.put("bootstrap.servers", "localhost:9093"); // Change to your Kafka broker(s)
    properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    Producer<String, String> producer = new KafkaProducer<>(properties);


    try {
      BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
      String line;
      float valueDiff = 0;
      float readValue = 0;
      float lastReadValue = 0;
      while ((line = reader.readLine()) != null) {
        long timestampMillis = Instant.now().toEpochMilli();
        readValue = Float.parseFloat(line);
        valueDiff = readValue - lastReadValue;
        lastReadValue = readValue;
        KafkaMessage message = new KafkaMessage(timestampMillis, deviceId, valueDiff);

        String jsonString = objectMapper.writeValueAsString(message);

        System.out.println("sending string = " + jsonString);

        ProducerRecord<String, String> record = new ProducerRecord<>(kafkaTopic, jsonString);
        producer.send(
                record,
                (recordMetadata, exception) -> {
                  if (exception != null) {
                    exception.printStackTrace();
                  } else {
                    System.out.println("Message sent to topic: " + recordMetadata.topic());
                    System.out.println(record.toString());
                  }
                });

        TimeUnit.SECONDS.sleep(3);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      producer.close();
    }
  }
}
