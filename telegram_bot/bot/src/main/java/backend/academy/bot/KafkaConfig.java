package backend.academy.bot;

import backend.academy.common.dto.kafka.request.KafkaBotRequest;
import backend.academy.common.dto.kafka.response.KafkaBotResponse;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // Отправка запроса в Kafka
    @Bean
    public ProducerFactory<String, KafkaBotRequest> requestproducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(
                JsonSerializer.TYPE_MAPPINGS, "botRequest:backend.academy.common.dto.kafka.request.KafkaBotRequest");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, KafkaBotRequest> requestKafkaTemplate() {
        KafkaTemplate<String, KafkaBotRequest> template = new KafkaTemplate<>(requestproducerFactory());
        template.setProducerListener(new LoggingProducerListener<>());
        return template;
    }

    //  Получение ответа от Kafka
    @Bean
    public ConsumerFactory<String, KafkaBotResponse> responseConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "bot-group");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(
                JsonDeserializer.TYPE_MAPPINGS,
                "botResponse:backend.academy.common.dto.kafka.response.KafkaBotResponse");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "backend.academy.common.dto.kafka.response.KafkaBotResponse");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // или строгое имя пакета
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaBotResponse> responseListenerContainerFactory(
            ConsumerFactory<String, KafkaBotResponse> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, KafkaBotResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
