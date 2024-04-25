package org.shlimtech.typesix.controller;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shlimtech.typesix.BaseTest;
import org.shlimtech.typesixbusinesslogic.service.core.RegistrationService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

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
        String content = getPageContent(EMAIL_PAGE);
        Assertions.assertTrue(content.contains(EMAIL_PAGE));
    }

    @Test
    public void emailPagePostTest() {
        final String email = "email";
        postPageContent(EMAIL_PAGE, Map.of("email", email), CODE_PAGE);
        verify(registrationService).beginRegistrationFlow(email);
    }

    @Test
    public void codePageGetTest() {
        String content = getPageContent(CODE_PAGE + "?email=" + TEST_EMAIL);
        Assertions.assertTrue(content.contains(CODE_PAGE));
    }

    @Test
    public void codePagePostTest() {
        postPageContent(CODE_PAGE, Map.of("email", TEST_EMAIL, "code", TEST_CODE), PASSWORD_CHANGE_PAGE);
        verify(registrationService).checkValidCode(TEST_EMAIL, TEST_CODE);
    }

    @Test
    public void passwordChangeGetTest() {
        String content = getPageContent(PASSWORD_CHANGE_PAGE + "?email=" + TEST_EMAIL + "&code=" + TEST_CODE);
        Assertions.assertTrue(content.contains(PASSWORD_SET_ENDPOINT));
    }

    @Test
    public void passwordChangePostTest() {
        postPageContent(PASSWORD_SET_ENDPOINT, Map.of("email", TEST_EMAIL, "code", TEST_CODE, "password", TEST_PASSWORD), LOGIN_ENDPOINT);
        verify(registrationService).endRegistrationFlow(TEST_EMAIL, TEST_CODE, TEST_PASSWORD);
    }

    protected String getPageContent(String path) {
        var url = origin() + path;
        var response = defaultClient
                .get()
                .uri(url)
                .retrieve();
        var ent = response.toEntity(String.class);
        var status = ent.getStatusCode();
        var body = ent.getBody();

        Assertions.assertTrue(status.is2xxSuccessful());

        return body;
    }

    protected void postPageContent(String path, Map<String, String> params, String redirect) {
        String mime = "";
        for (var entry : params.entrySet()) {
            mime += entry.getKey() + "=" + entry.getValue() + "&";
        }
        mime = mime.substring(0, mime.length() - 1);

        var url = origin() + path;
        var response = defaultClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(mime)
                .retrieve();
        var ent = response.toEntity(String.class);
        var headers = ent.getHeaders();

        String location = headers.getFirst("Location");

        Assertions.assertTrue(ent.getStatusCode().is3xxRedirection());
        Assertions.assertTrue(location.contains(redirect));
    }

}
