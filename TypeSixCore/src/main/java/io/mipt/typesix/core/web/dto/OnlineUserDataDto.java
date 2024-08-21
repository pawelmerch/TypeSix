package io.mipt.typesix.core.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OnlineUserDataDto {
    String email;
    String lastError;
}
