package org.shlimtech.typesix.security;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.shlimtech.typesix.BaseTest;
import org.shlimtech.typesix.controller.AuthController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

import static org.shlimtech.typesix.security.EndpointsList.LOGIN_ENDPOINT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log
public class AuthControllerTests extends BaseTest {
    @Value("${type6.clients.type-7.client-redirect-uri}")
    private String type7RedirectUrl;

    @Value("${type6.clients.type-8.client-redirect-uri}")
    private String type8RedirectUrl;

    @Test
    public void type7ClientTest() {
        checkClientRedirection(type7RedirectUrl, AuthController.yandexAuthUrl);
    }

    @Test
    public void type8ClientTest() {
        checkClientRedirection(type8RedirectUrl, AuthController.githubAuthUrl);
    }

    @SneakyThrows
    private void checkClientRedirection(String redirectUrl, String providerUrl) {
        final String sessionAttr = "SPRING_SECURITY_SAVED_REQUEST";
        final String requestAttr = "redirect_uri";
        final String headerName = "Location";

        final DefaultSavedRequest savedRequest = new DefaultSavedRequest.Builder().setScheme("").setParameters(Map.of(requestAttr, new String[]{redirectUrl})).build();
        var response = mockMvc.perform(get(LOGIN_ENDPOINT).sessionAttr(sessionAttr, savedRequest))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse();
        Assert.isTrue(Objects.equals(response.getHeader(headerName), providerUrl), "bad redirection");
    }

}
