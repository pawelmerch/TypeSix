package org.shlimtech.typesix.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shlimtech.typesix.BaseTest;
import org.shlimtech.typesixbusinesslogic.domain.model.User;
import org.shlimtech.typesixbusinesslogic.service.core.AuthenticationService;
import org.shlimtech.typesixbusinesslogic.service.core.RegistrationService;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.shlimtech.typesix.web.EndpointsList.*;

public class RegistrationFlowTests extends BaseTest {
    private static final String TEST_EMAIL = "email";
    private static final String TEST_CODE = "code";
    private static final String TEST_PASSWORD = "password";

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void testFlow() {
        step1();
        step2();
        step3();
        step4();
        step5();
        step6();
        step7();
        step8();
    }

    private void step1() {
        String content = getPageContent(LOGIN_PAGE);
        Assertions.assertTrue(content.contains(REGISTRATION_EMAIL_PAGE));
    }

    private void step2() {
        String content = getPageContent(REGISTRATION_EMAIL_PAGE);
        Assertions.assertTrue(content.contains(REGISTRATION_EMAIL_ENDPOINT));
    }

    private void step3() {
        postWithFormMimeAndRedirect(REGISTRATION_EMAIL_ENDPOINT, Map.of("email", TEST_EMAIL), REGISTRATION_CODE_PAGE);
        verify(registrationService).beginRegistrationFlow(TEST_EMAIL);
    }

    private void step4() {
        String content = getPageContent(REGISTRATION_CODE_PAGE + "?email=" + TEST_EMAIL);
        Assertions.assertTrue(content.contains(REGISTRATION_CODE_ENDPOINT));
    }

    private void step5() {
        postWithFormMimeAndRedirect(REGISTRATION_CODE_ENDPOINT, Map.of("email", TEST_EMAIL, "code", TEST_CODE), REGISTRATION_PASSWORD_SET_PAGE);
        verify(registrationService).checkValidCode(TEST_EMAIL, TEST_CODE);
    }

    private void step6() {
        String content = getPageContent(REGISTRATION_PASSWORD_SET_PAGE + "?email=" + TEST_EMAIL + "&code=" + TEST_CODE);
        Assertions.assertTrue(content.contains(REGISTRATION_PASSWORD_SET_ENDPOINT));
    }

    private void step7() {
        postWithFormMimeAndRedirect(REGISTRATION_PASSWORD_SET_ENDPOINT, Map.of("email", TEST_EMAIL, "code", TEST_CODE, "password", TEST_PASSWORD), LOGIN_PAGE);
        verify(registrationService).endRegistrationFlow(TEST_EMAIL, TEST_CODE, TEST_PASSWORD);
    }

    private void step8() {
        when(authenticationService.loadUser(TEST_EMAIL)).thenReturn(User.builder().email(TEST_EMAIL).password(TEST_PASSWORD).build());
        postWithFormMimeAndRedirect(FORM_LOGIN_ENDPOINT, Map.of("username", TEST_EMAIL, "password", TEST_PASSWORD), SUCCESS_LOGIN_PAGE);
    }
}
