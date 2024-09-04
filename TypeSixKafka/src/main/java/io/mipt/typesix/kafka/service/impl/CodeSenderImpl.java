package io.mipt.typesix.kafka.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mipt.typesix.businesslogic.service.core.exception.CodeSenderException;
import io.mipt.typesix.businesslogic.service.core.spi.CodeSender;
import io.mipt.typesix.kafka.dto.RegistrationQueueMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log
@Service
@RequiredArgsConstructor
@Profile("!local-embedded")
public class CodeSenderImpl implements CodeSender {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, RegistrationQueueMessageDto> kafkaTemplate;

    @Value("${type-6.kafka.email-code-sending-topic-name}")
    private String topicName;

    @Override
    public void sendCode(String email, String code) throws CodeSenderException, IllegalStateException {
        try {
            var object = RegistrationQueueMessageDto
                    .builder()
                    .code(code)
                    .email(email)
                    .build();
            log.info("[" + object + "]");
            kafkaTemplate.send(topicName, object);
        } catch (Exception chain) {
            throw new IllegalStateException(chain);
        }
    }
}
