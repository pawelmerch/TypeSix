package io.mipt.typesix.rabbit.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mipt.typesix.businesslogic.service.core.exception.CodeSenderException;
import io.mipt.typesix.businesslogic.service.core.spi.CodeSender;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class CodeSenderImpl implements CodeSender {
    private final AmqpTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${type6.registration-email-send-queue-name}")
    private String queueName;

    @Override
    public void sendCode(String email, String code) throws CodeSenderException {
        try {
            rabbitTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(RegistrationQueueMessage
                    .builder()
                    .code(code)
                    .email(email)
                    .build()));
        } catch (Exception chain) {
            throw new CodeSenderException(chain);
        }
    }

    @Data
    @Builder
    public static class RegistrationQueueMessage implements Serializable {
        private String code;
        private String email;
    }
}
