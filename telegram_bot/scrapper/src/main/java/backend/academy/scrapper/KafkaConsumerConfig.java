package backend.academy.scrapper;

import backend.academy.common.dto.kafka.request.KafkaBotRequest;
import backend.academy.common.dto.kafka.response.KafkaBotResponse;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Autowired
    private ScrapperConfig scrapperConfig;

    // Получение ответа от Kafka
    @Bean
    public ConsumerFactory<String, KafkaBotRequest> consumerFactory(ScrapperConfig config) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.kafka().consumer().groupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        //        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        //        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "backend.academy.common.dto.kafka.request.KafkaBotRequest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaBotRequest> kafkaListenerContainerFactory(
            ConsumerFactory<String, KafkaBotRequest> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, KafkaBotRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    // Отправка ответа в Kafka
    @Bean
    public ProducerFactory<String, KafkaBotResponse> responseProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(
                JsonSerializer.TYPE_MAPPINGS, "botResponse:backend.academy.common.dto.kafka.response.KafkaBotResponse");
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, KafkaBotResponse> responseKafkaTemplate() {
        KafkaTemplate<String, KafkaBotResponse> template = new KafkaTemplate<>(responseProducerFactory());
        template.setProducerListener(new LoggingProducerListener<>());
        return template;
    }

    //    @Bean
    //    public ConsumerFactory<String, KafkaBotRequest> requestConsumerFactory() {
    //        Map<String, Object> props = new HashMap<>();
    //        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
    //        props.put(ConsumerConfig.GROUP_ID_CONFIG, "scrapper-group");
    //        props.put(JsonDeserializer.TRUSTED_PACKAGES, "backend.academy.common.dto.kafka.request");
    //        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE,
    // "backend.academy.common.dto.kafka.request.KafkaBotRequest");
    //        props.put(JsonDeserializer.TYPE_MAPPINGS,
    // "kafkaBotRequest:backend.academy.common.dto.kafka.request.KafkaBotRequest");
    //
    //        return new DefaultKafkaConsumerFactory<>(
    //            props,
    //            new StringDeserializer(),
    //            new JsonDeserializer<>(KafkaBotRequest.class)
    //        );
    //    }
    //
    //    @Bean
    //    public KafkaTemplate<String, KafkaBotResponse> responseKafkaTemplate() {
    //        return new KafkaTemplate<>(responseProducerFactory());
    //    }
    //
    //    @Bean
    //    public ProducerFactory<String, KafkaBotResponse> responseProducerFactory() {
    //        Map<String, Object> config = new HashMap<>();
    //        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
    //        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    //        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    //        config.put(JsonSerializer.TYPE_MAPPINGS,
    // "botResponse:backend.academy.common.dto.kafka.response.KafkaBotResponse");
    //        return new DefaultKafkaProducerFactory<>(config);
    //    }
}
