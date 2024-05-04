package org.shlimtech.typesix;

import org.junit.jupiter.api.Assertions;
import org.shlimtech.typesix.utils.HttpRequestInput;
import org.shlimtech.typesix.utils.HttpResponseOutput;
import org.shlimtech.typesix.web.debug.DebugUsersConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestClient;

import java.util.Base64;
import java.util.Map;

import static org.shlimtech.typesix.web.EndpointsList.*;

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

        // RABBIT
        "spring.rabbitmq.host=localhost",
        "spring.rabbitmq.port=7654",
        "spring.rabbitmq.username=user",
        "spring.rabbitmq.password=pass",

        // SWAGGER
        "springdoc.api-docs.path=" + SPRING_DOC_PATH,
        "springdoc.swagger-ui.path=" + SWAGGER_UI_BASE_PATH
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class BaseTest {
    @LocalServerPort
    protected int port;

    protected final RestClient defaultClient = RestClient.create();

    protected String origin() {
        return "http://localhost:" + port;
    }

    protected HttpResponseOutput get(HttpRequestInput input, String path) {
        var url = origin() + path;
        var spec = defaultClient.get();

        if (input.hasCookie()) {
            spec.header("Cookie", "JSESSIONID=" + input.getCookie());
        }
        if (input.hasAuth()) {
            spec.header("Authorization", "Basic " + new String(Base64.getEncoder().encode((input.getAuth()).getBytes())));
        }
        if (input.hasMime()) {
            String mime = "";
            for (var entry : input.getMime().entrySet()) {
                mime += entry.getKey() + "=" + entry.getValue() + "&";
            }
            mime = mime.substring(0, mime.length() - 1);
            url += "?" + mime;
        }

        spec.uri(url);

        var response = spec.retrieve();
        ResponseEntity<?> ent;
        if (input.isBodyRequested()) {
            ent = response.toEntity(String.class);
        } else {
            ent = response.toBodilessEntity();
        }

        var status = ent.getStatusCode();
        var headers = ent.getHeaders();

        HttpResponseOutput output = HttpResponseOutput.builder().build();

        if (headers.containsKey("set-cookie")) {
            String cookieLine = headers.getFirst("set-cookie");
            String sessionCookie = cookieLine.substring(cookieLine.indexOf('=') + 1, cookieLine.indexOf(';'));
            output.setCookie(sessionCookie);
        }
        if (headers.containsKey("Location")) {
            output.setLocation(headers.getFirst("Location"));
        }
        if (ent.getBody() != null && !ent.getBody().toString().isEmpty()) {
            output.setContent(ent.getBody().toString());
        }
        output.setStatusCode(status);

        return output;
    }

    protected HttpResponseOutput post(HttpRequestInput input, String path) {
        var url = origin() + path;
        var spec = defaultClient.post();

        if (input.hasCookie()) {
            spec.header("Cookie", "JSESSIONID=" + input.getCookie());
        }
        if (input.hasAuth()) {
            spec.header("Authorization", "Basic " + new String(Base64.getEncoder().encode((input.getAuth()).getBytes())));
        }
        if (input.hasMime()) {
            String mime = "";
            for (var entry : input.getMime().entrySet()) {
                mime += entry.getKey() + "=" + entry.getValue() + "&";
            }
            mime = mime.substring(0, mime.length() - 1);
            spec.contentType(MediaType.APPLICATION_FORM_URLENCODED);
            spec.body(mime);
        }

        spec.uri(url);

        var response = spec.retrieve();
        ResponseEntity<?> ent;
        if (input.isBodyRequested()) {
            ent = response.toEntity(String.class);
        } else {
            ent = response.toBodilessEntity();
        }

        var status = ent.getStatusCode();
        var headers = ent.getHeaders();

        HttpResponseOutput output = HttpResponseOutput.builder().build();

        if (headers.containsKey("set-cookie")) {
            String cookieLine = headers.getFirst("set-cookie");
            String sessionCookie = cookieLine.substring(cookieLine.indexOf('=') + 1, cookieLine.indexOf(';'));
            output.setCookie(sessionCookie);
        }
        if (headers.containsKey("Location")) {
            output.setLocation(headers.getFirst("Location"));
        }
        if (ent.getBody() != null && !ent.getBody().toString().isEmpty()) {
            output.setContent(ent.getBody().toString());
        }
        output.setStatusCode(status);

        return output;
    }

    public String login() {
        var out = post(HttpRequestInput.builder().mime(Map.of("username", DebugUsersConfig.USER1_EMAIL, "password", DebugUsersConfig.USER1)).build(), FORM_LOGIN_ENDPOINT);
        Assertions.assertTrue(out.getStatusCode().is3xxRedirection());
        Assertions.assertTrue(out.getLocation().contains(SUCCESS_LOGIN_PAGE));
        return out.getCookie();
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

    protected void postWithFormMimeAndRedirect(String path, Map<String, String> params, String redirect) {
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
