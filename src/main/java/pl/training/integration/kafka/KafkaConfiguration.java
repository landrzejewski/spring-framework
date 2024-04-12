package pl.training.integration.kafka;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

/*  @Bean
    public ProducerFactory<String, MessageDto> producerFactory() {
        var serializer = new JsonSerializer<MessageDto>();
        serializer.setAddTypeInfo(true);
        var properties = new HashMap<String, Object>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new DefaultKafkaProducerFactory<>(properties, new StringSerializer(), serializer);
    }

    @Bean
    public ConsumerFactory<String, MessageDto> consumerFactory() {
        var deserializer = new JsonDeserializer<MessageDto>();
        deserializer.addTrustedPackages("pl.training.shop.integrations.kafka");
        var properties = new HashMap<String, Object>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "chat");
        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer);
    }

    @Bean
    public NewTopic mainTopic() {
        return TopicBuilder.name("messages").build();
    }

    @Bean
    public KafkaTemplate<String, MessageDto> kafkaTemplate(ProducerFactory<String, MessageDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MessageDto>> kafkaListenerContainerFactory(ConsumerFactory<String, MessageDto> consumerFactory) {
        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, MessageDto>();
        containerFactory.setConsumerFactory(consumerFactory);
        return containerFactory;
    }*/

}
