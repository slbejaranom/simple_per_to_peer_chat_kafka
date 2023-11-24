package org.example.services;

import static org.example.utils.Constants.TOPIC_NAME;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Duration;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.example.Main;
import org.example.dto.MessageDto;

@RequiredArgsConstructor
public class KafkaListener extends Thread {

  private final Consumer<String, String> kafkaConsumer;
  private final ObjectMapper objectMapper;

  @Override
  public void run() {
    try {
      startPollingForMessages();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  void startPollingForMessages() throws IOException {
    while (true) {
      ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
      for (ConsumerRecord<String, String> record : records) {
        if(record.value().isBlank() || record.value().isEmpty()){
          break;
        }
        MessageDto messageDto = objectMapper.readValue(record.value(), MessageDto.class);
        if (!Main.name.equals(messageDto.getName())) {
          System.out.println(messageDto.getName() + " says: " + messageDto.getMessage());
        }
      }
    }
  }
}
