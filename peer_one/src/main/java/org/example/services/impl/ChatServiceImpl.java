package org.example.services.impl;

import static org.example.utils.Constants.TOPIC_NAME;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.dto.MessageDto;
import org.example.services.ChatService;
import org.example.services.KafkaListener;

@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final Producer<String, String> kafkaProducer;
  private final Consumer<String, String> kafkaConsumer;
  private final KafkaListener kafkaListener;
  private final ObjectMapper objectMapper;

  @Override
  public void send(MessageDto messageDto) throws IOException {
    ProducerRecord<String, String> kafkaRecord = new ProducerRecord<>(TOPIC_NAME, objectMapper.writeValueAsString(messageDto));
    kafkaProducer.send(kafkaRecord);
  }

  @Override
  public void startPollingMessages() {
    kafkaConsumer.subscribe(List.of(TOPIC_NAME));
    kafkaListener.start();
  }

  @Override
  public void stopPollingMessages() {
    kafkaListener.stop();
  }
}
