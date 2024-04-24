package org.shlimtech.typesix.security;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.shlimtech.typesix.BaseTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import static org.shlimtech.typesix.security.EndpointsList.LOGIN_ENDPOINT;
import static org.shlimtech.typesix.security.EndpointsList.OAUTH2_AUTHORIZATION_ENDPOINT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Oauth2FlowTests extends BaseTest {
    @Value("${type6.clients.type-7.client-redirect-uri}")
    private String type7RedirectUrl;

    @Value("${type-6.clients.type-7.client-id}")
    private String type7ClientId;

    @Test
    @SneakyThrows
    public void authorizationEndpointTest() {
        var response = mockMvc.perform(get(OAUTH2_AUTHORIZATION_ENDPOINT + "?response_type=code&client_id=" + type7ClientId + "&redirect_uri=" + type7RedirectUrl)).andExpect(status().is3xxRedirection()).andReturn().getResponse();
        Assert.isTrue(response.getHeader("Location").contains(LOGIN_ENDPOINT), "bad redirect");
    }

    @Test
    @SneakyThrows
    public void loginPageEndpointTest() {
        mockMvc.perform(get(LOGIN_ENDPOINT)).andExpect(status().isOk());
    }

}
