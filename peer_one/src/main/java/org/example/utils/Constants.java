package org.example.utils;

import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class Constants {

  private static final String bootstrapServers = "localhost:9092";

  public static final String TOPIC_NAME = "chat-topic";

  //Producer properties
  private static Properties kafkaProducerProperties = new Properties();

  public static Properties getKafkaProducerProperties() {
    kafkaProducerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    kafkaProducerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        StringSerializer.class.getName());
    kafkaProducerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        StringSerializer.class.getName());
    return kafkaProducerProperties;
  }
  //End producer properties

  //Consumer properties
  String groupId = "my-fourth-application";
  String topic = "demo_java";
  private static Properties kafkaConsumerProperties = new Properties();

  public static Properties getKafkaConsumerProperties() {
    kafkaConsumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    kafkaConsumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "peer-1");
    kafkaConsumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class.getName());
    kafkaConsumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class.getName());
    kafkaConsumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    return kafkaConsumerProperties;
  }
  //End consumer properties
}
