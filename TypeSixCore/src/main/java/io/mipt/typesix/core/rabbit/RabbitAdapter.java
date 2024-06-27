package io.mipt.typesix.core.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mipt.typesix.businesslogic.service.core.RegistrationService;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
@Log
public class RabbitAdapter implements BiConsumer<String, String> {
    private final RegistrationService registrationService;
    private final AmqpTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${type6.registration-email-send-queue-name}")
    private String queueName;

    @PostConstruct
    @SneakyThrows
    public void initRegistrationService() {
        registrationService.setCodeSender(this);
    }

    @Override
    @SneakyThrows
    public void accept(String code, String email) {
        rabbitTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(RegistrationQueueMessage.builder().code(code).email(email).build()));
    }

    @Data
    @Builder
    public static class RegistrationQueueMessage implements Serializable {
        private String code;
        private String email;
    }
}
