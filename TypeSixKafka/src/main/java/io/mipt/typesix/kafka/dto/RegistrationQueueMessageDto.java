package io.mipt.typesix.kafka.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationQueueMessageDto {
    private String code;
    private String email;
}
