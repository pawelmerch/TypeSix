package org.shlimtech.typesix.controller;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shlimtech.typesix.BaseTest;
import org.shlimtech.typesixbusinesslogic.service.core.RegistrationService;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.shlimtech.typesix.security.EndpointsList.*;

@Log
public class RegistrationControllerTests extends BaseTest {
    private static final String TEST_EMAIL = "email";
    private static final String TEST_CODE = "code";
    private static final String TEST_PASSWORD = "password";

    @MockBean
    private RegistrationService registrationService;

    @Test
    public void emailPageGetTest() {
        String content = getPageContent(REGISTRATION_EMAIL_PAGE);
        Assertions.assertTrue(content.contains(REGISTRATION_EMAIL_ENDPOINT));
    }
    
    @Test
    public void emailPagePostTest() {
        postWithFormMimeAndRedirect(REGISTRATION_EMAIL_ENDPOINT, Map.of("email", TEST_EMAIL), REGISTRATION_CODE_PAGE);
        verify(registrationService).beginRegistrationFlow(TEST_EMAIL);
    }

    @Test
    public void codePageGetTest() {
        String content = getPageContent(REGISTRATION_CODE_PAGE + "?email=" + TEST_EMAIL);
        Assertions.assertTrue(content.contains(REGISTRATION_CODE_ENDPOINT));
    }

    @Test
    public void codePagePostTest() {
        postWithFormMimeAndRedirect(REGISTRATION_CODE_ENDPOINT, Map.of("email", TEST_EMAIL, "code", TEST_CODE), REGISTRATION_PASSWORD_SET_PAGE);
        verify(registrationService).checkValidCode(TEST_EMAIL, TEST_CODE);
    }

    @Test
    public void passwordChangeGetTest() {
        String content = getPageContent(REGISTRATION_PASSWORD_SET_PAGE + "?email=" + TEST_EMAIL + "&code=" + TEST_CODE);
        Assertions.assertTrue(content.contains(REGISTRATION_PASSWORD_SET_ENDPOINT));
    }

    @Test
    public void passwordChangePostTest() {
        postWithFormMimeAndRedirect(REGISTRATION_PASSWORD_SET_ENDPOINT, Map.of("email", TEST_EMAIL, "code", TEST_CODE, "password", TEST_PASSWORD), LOGIN_PAGE);
        verify(registrationService).endRegistrationFlow(TEST_EMAIL, TEST_CODE, TEST_PASSWORD);
    }
}
