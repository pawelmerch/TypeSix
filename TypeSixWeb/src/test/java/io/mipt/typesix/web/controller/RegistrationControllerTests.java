package io.mipt.typesix.web.controller;

import io.mipt.typesix.web.BaseTest;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.mipt.typesix.web.EndpointsList.*;
import static org.mockito.Mockito.verify;

@Log
public class RegistrationControllerTests extends BaseTest {
    private static final String TEST_EMAIL = "email";
    private static final String TEST_CODE = "code";
    private static final String TEST_PASSWORD = "password";

    @Test
    public void emailPagePostTest() {
        postWithFormMimeAndExpect2xx(REGISTRATION_EMAIL_ENDPOINT, Map.of("email", TEST_EMAIL));
        verify(registrationService).beginRegistrationFlow(TEST_EMAIL);
    }

    @Test
    public void codePagePostTest() {
        postWithFormMimeAndExpect2xx(REGISTRATION_CODE_ENDPOINT, Map.of("email", TEST_EMAIL, "code", TEST_CODE));
        verify(registrationService).checkValidCode(TEST_EMAIL, TEST_CODE);
    }

    @Test
    public void passwordChangePostTest() {
        postWithFormMimeAndExpect2xx(REGISTRATION_PASSWORD_SET_ENDPOINT, Map.of("email", TEST_EMAIL, "code", TEST_CODE, "password", TEST_PASSWORD));
        verify(registrationService).endRegistrationFlow(TEST_EMAIL, TEST_CODE, TEST_PASSWORD);
    }
}
