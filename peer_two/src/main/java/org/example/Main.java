package org.example;

import static org.example.utils.Constants.getKafkaConsumerProperties;
import static org.example.utils.Constants.getKafkaProducerProperties;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Scanner;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.example.dto.MessageDto;
import org.example.services.ChatService;
import org.example.services.KafkaListener;
import org.example.services.impl.ChatServiceImpl;

public class Main {

  public static String name;
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final Producer<String, String> kafkaProducer = new KafkaProducer<>(
      getKafkaProducerProperties());
  private static final Consumer<String, String> kafkaConsumer = new KafkaConsumer<>(
      getKafkaConsumerProperties()
  );
  private static final KafkaListener kafkaListener = new KafkaListener(kafkaConsumer, objectMapper);
  private static final ChatService chatService = new ChatServiceImpl(kafkaProducer, kafkaConsumer,
      kafkaListener, objectMapper);

  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    chatService.startPollingMessages();
    System.out.println("Insert your chat alias and press enter\n");
    name = scanner.nextLine();
    System.out.println(
        "Now type your message, please type 'exit' two times without the quotes to exit the chat\n");
    String message = "";
    ;
    while (true) {
      message = scanner.nextLine();
      if (message.equals("exit")) {
        break;
      }
      MessageDto messageDto = new MessageDto(name, message);
      chatService.send(messageDto);
    }
    kafkaProducer.close();
    chatService.stopPollingMessages();
  }
}