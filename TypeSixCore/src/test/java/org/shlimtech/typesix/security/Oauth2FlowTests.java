package org.shlimtech.typesix.security;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.shlimtech.typesix.BaseTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

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
        var response = mockMvc.perform(get("/oauth2/authorize?response_type=code&client_id=" + type7ClientId + "&redirect_uri=" + type7RedirectUrl)).andExpect(status().is3xxRedirection()).andReturn().getResponse();
        Assert.isTrue(response.getHeader("Location").contains("/login"), "bad redirect");
    }

    @Test
    @SneakyThrows
    public void loginPageEndpointTest() {
        mockMvc.perform(get("/login")).andExpect(status().isOk());
    }

}
