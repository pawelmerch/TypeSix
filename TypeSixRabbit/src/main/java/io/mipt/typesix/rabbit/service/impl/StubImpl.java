package io.mipt.typesix.rabbit.service.impl;

import io.mipt.typesix.businesslogic.service.core.exception.CodeSenderException;
import io.mipt.typesix.businesslogic.service.core.spi.CodeSender;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Log
@Component
@Profile("local-embedded")
public class StubImpl implements CodeSender {
    @Override
    public void sendCode(String email, String code) throws CodeSenderException {
        log.info("Sending code " + code + " to " + email);
    }
}
