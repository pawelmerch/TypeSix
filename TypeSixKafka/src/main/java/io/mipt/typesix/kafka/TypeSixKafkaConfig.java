package io.mipt.typesix.kafka;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
@ComponentScan
@PropertySource("classpath:kafka.properties")
public class TypeSixKafkaConfig {
}
