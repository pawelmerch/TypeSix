package org.shlimtech.typesix.security;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=sa",
        "spring.profiles.active=debug",
        "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://google.com",
        "type-6.issuer=http://localhost:7777",
        "server.ssl.enabled=false",

        "spring.security.oauth2.client.registration.github.clientId=aaa",
        "spring.security.oauth2.client.registration.github.clientSecret=aaa",

        "spring.security.oauth2.client.registration.yandex.clientId=bbb",
        "spring.security.oauth2.client.registration.yandex.clientSecret=bbb",
        "spring.security.oauth2.client.registration.yandex.redirect-uri=http://localhost:7777/login/oauth2/code/yandex",

        "type6.clients.type-7.client-id=ccc",
        "type6.clients.type-7.client-secret=ccc",
        "type6.clients.type-7.client-redirect-uri=http://localhost:7777/code",
        "type6.clients.type-7.client-hostname=http://localhost:7777",

        "type6.clients.type-8.client-id=ddd",
        "type6.clients.type-8.client-secret=ddd",
        "type6.clients.type-8.client-redirect-uri=http://localhost:7777",
        "type6.clients.type-8.client-hostname=http://localhost:7777"
})
@ComponentScan(basePackages = {"org.shlimtech"})
@ComponentScan(basePackages = "org.shlimtech")
@EntityScan(basePackages = "org.shlimtech")
@AutoConfigureMockMvc
@Log
public class Oauth2FlowTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void authorizationEndpointTest() {
        var response = mockMvc.perform(get("http://localhost:7777/oauth2/authorize?response_type=code&client_id=ccc&redirect_uri=http://localhost:7777/code")).andExpect(status().is3xxRedirection()).andReturn().getResponse();
        log.info("status: " + response.getStatus() + " " + response.getHeader("Location"));
    }

    @Test
    @SneakyThrows
    public void loginPageEndpointTest() {
        var response = mockMvc.perform(get("http://localhost:7777/login")).andExpect(status().isOk()).andReturn().getResponse();
        log.info(response.toString());
    }

}
