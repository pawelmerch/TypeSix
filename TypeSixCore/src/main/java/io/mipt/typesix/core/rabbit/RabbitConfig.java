package io.mipt.typesix.core.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {
    @Bean
    public Queue registrationQueue(@Value("${type6.registration-email-send-queue-name}") String queueName) {
        return new Queue(queueName);
    }
}
