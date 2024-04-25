package org.shlimtech.typesix;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {
        // H2 DATA BASE
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=tmp_user",
        "spring.datasource.password=tmp_password",

        // PROFILE
        "spring.profiles.active=debug",

        // OTHER
        "type-6.selfUrl=http://localhost:7777",
        "server.ssl.enabled=false",

        // GITHUB OAUTH2 PROVIDER
        "spring.security.oauth2.client.registration.github.clientId=githubTmpClientId",
        "spring.security.oauth2.client.registration.github.clientSecret=githubTmpClientSecret",
        "spring.security.oauth2.client.registration.github.redirect-uri=/tmp/github",

        // YANDEX OAUTH2 PROVIDER
        "spring.security.oauth2.client.registration.yandex.clientId=yandexTmpClientId",
        "spring.security.oauth2.client.registration.yandex.clientSecret=yandexTmpClientSecret",
        "spring.security.oauth2.client.registration.yandex.redirect-uri=/tmp/yandex",

        // TEST-TYPE
        "type6.clients.test-type.client-id=testClientId",
        "type6.clients.test-type.client-secret=testClientSecret",
        "type6.clients.test-type.client-redirect-uri=http://localhost:5555/tmp/type7/code",
        "type6.clients.test-type.client-hostname=http://localhost:5555",
        "type6.clients.test-type.auth-method=all",

        // TYPE-7 CLIENT
        "type6.clients.type-7.client-id=type7tmpClientId",
        "type6.clients.type-7.client-secret=type7tmpClientSecret",
        "type6.clients.type-7.client-redirect-uri=http://localhost:4444/tmp/type7/code",
        "type6.clients.type-7.client-hostname=http://localhost:4444",
        "type6.clients.type-7.auth-method=yandex",

        // TYPE-8 CLIENT
        "type6.clients.type-8.client-id=type8tmpClientId",
        "type6.clients.type-8.client-secret=type8tmpClientSecret",
        "type6.clients.type-8.client-redirect-uri=/tmp/type8/code",
        "type6.clients.type-8.client-hostname=type8.org",
        "type6.clients.type-8.auth-method=github",

        //"logging.level.org.springframework.security: TRACE"
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class BaseTest {
    @LocalServerPort
    protected int port;

    protected String origin() {
        return "http://localhost:" + port;
    }
}
