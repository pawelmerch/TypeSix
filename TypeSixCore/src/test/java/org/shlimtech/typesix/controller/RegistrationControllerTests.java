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
        String content = getPageContent(EMAIL_ENDPOINT);
        Assertions.assertTrue(content.contains(EMAIL_ENDPOINT));
    }

    @Test
    public void emailPagePostTest() {
        postWithFormMimeAndRedirect(EMAIL_ENDPOINT, Map.of("email", TEST_EMAIL), CODE_ENDPOINT);
        verify(registrationService).beginRegistrationFlow(TEST_EMAIL);
    }

    @Test
    public void codePageGetTest() {
        String content = getPageContent(CODE_ENDPOINT + "?email=" + TEST_EMAIL);
        Assertions.assertTrue(content.contains(CODE_ENDPOINT));
    }

    @Test
    public void codePagePostTest() {
        postWithFormMimeAndRedirect(CODE_ENDPOINT, Map.of("email", TEST_EMAIL, "code", TEST_CODE), PASSWORD_SET_ENDPOINT);
        verify(registrationService).checkValidCode(TEST_EMAIL, TEST_CODE);
    }

    @Test
    public void passwordChangeGetTest() {
        String content = getPageContent(PASSWORD_SET_ENDPOINT + "?email=" + TEST_EMAIL + "&code=" + TEST_CODE);
        Assertions.assertTrue(content.contains(PASSWORD_SET_ENDPOINT));
    }

    @Test
    public void passwordChangePostTest() {
        postWithFormMimeAndRedirect(PASSWORD_SET_ENDPOINT, Map.of("email", TEST_EMAIL, "code", TEST_CODE, "password", TEST_PASSWORD), LOGIN_ENDPOINT);
        verify(registrationService).endRegistrationFlow(TEST_EMAIL, TEST_CODE, TEST_PASSWORD);
    }

}
