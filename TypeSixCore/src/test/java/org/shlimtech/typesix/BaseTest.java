package org.shlimtech.typesix;

import org.shlimtech.typesixdatabasecommon.TypeSixDatabaseCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@TestPropertySource(properties = {
        // H2 DATA BASE
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=tmp_user",
        "spring.datasource.password=tmp_password",

        // PROFILE
        "spring.profiles.active=release",

        // OTHER
        "type-6.issuer=http://localhost:7777",
        "server.ssl.enabled=false",

        // GITHUB OAUTH2 PROVIDER
        "spring.security.oauth2.client.registration.github.clientId=githubTmpClientId",
        "spring.security.oauth2.client.registration.github.clientSecret=githubTmpClientSecret",
        "spring.security.oauth2.client.registration.github.redirect-uri=/tmp/github",

        // YANDEX OAUTH2 PROVIDER
        "spring.security.oauth2.client.registration.yandex.clientId=yandexTmpClientId",
        "spring.security.oauth2.client.registration.yandex.clientSecret=yandexTmpClientSecret",
        "spring.security.oauth2.client.registration.yandex.redirect-uri=/tmp/yandex",

        // TYPE-7 CLIENT
        "type6.clients.type-7.client-id=type7tmpClientId",
        "type6.clients.type-7.client-secret=type7tmpClientSecret",
        "type6.clients.type-7.client-redirect-uri=/tmp/type7/code",
        "type6.clients.type-7.client-hostname=type7.org",
        "type6.clients.type-7.auth-method=yandex",

        // TYPE-8 CLIENT
        "type6.clients.type-8.client-id=type8tmpClientId",
        "type6.clients.type-8.client-secret=type8tmpClientSecret",
        "type6.clients.type-8.client-redirect-uri=/tmp/type8/code",
        "type6.clients.type-8.client-hostname=type8.org",
        "type6.clients.type-8.auth-method=github",
})
@TypeSixDatabaseCommon
@AutoConfigureMockMvc
public class BaseTest {
    @Autowired
    protected MockMvc mockMvc;
}
