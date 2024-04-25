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
import static org.shlimtech.typesix.security.EndpointsList.*;

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
        String content = getPageContent(LOGIN_ENDPOINT);
        Assertions.assertTrue(content.contains(EMAIL_ENDPOINT));
    }

    private void step2() {
        String content = getPageContent(EMAIL_ENDPOINT);
        Assertions.assertTrue(content.contains(EMAIL_ENDPOINT));
    }

    private void step3() {
        postWithFormMimeAndRedirect(EMAIL_ENDPOINT, Map.of("email", TEST_EMAIL), CODE_ENDPOINT);
        verify(registrationService).beginRegistrationFlow(TEST_EMAIL);
    }

    private void step4() {
        String content = getPageContent(CODE_ENDPOINT + "?email=" + TEST_EMAIL);
        Assertions.assertTrue(content.contains(CODE_ENDPOINT));
    }

    private void step5() {
        postWithFormMimeAndRedirect(CODE_ENDPOINT, Map.of("email", TEST_EMAIL, "code", TEST_CODE), PASSWORD_SET_ENDPOINT);
        verify(registrationService).checkValidCode(TEST_EMAIL, TEST_CODE);
    }

    private void step6() {
        String content = getPageContent(PASSWORD_SET_ENDPOINT + "?email=" + TEST_EMAIL + "&code=" + TEST_CODE);
        Assertions.assertTrue(content.contains(PASSWORD_SET_ENDPOINT));
    }

    private void step7() {
        postWithFormMimeAndRedirect(PASSWORD_SET_ENDPOINT, Map.of("email", TEST_EMAIL, "code", TEST_CODE, "password", TEST_PASSWORD), LOGIN_ENDPOINT);
        verify(registrationService).endRegistrationFlow(TEST_EMAIL, TEST_CODE, TEST_PASSWORD);
    }

    private void step8() {
        when(authenticationService.loadUser(TEST_EMAIL)).thenReturn(User.builder().email(TEST_EMAIL).password(TEST_PASSWORD).build());
        postWithFormMimeAndRedirect(FORM_LOGIN_ENDPOINT, Map.of("username", TEST_EMAIL, "password", TEST_PASSWORD), SUCCESS_LOGIN_PAGE);
    }

}
