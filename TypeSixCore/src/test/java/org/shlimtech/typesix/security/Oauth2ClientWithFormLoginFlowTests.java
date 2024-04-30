package org.shlimtech.typesix.security;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shlimtech.typesix.BaseTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import java.util.Base64;
import java.util.Map;

import static org.shlimtech.typesix.security.EndpointsList.*;

@Log
public class Oauth2ClientWithFormLoginFlowTests extends BaseTest {
    @Value("${type6.clients.test-type.client-id}")
    private String clientId;
    @Value("${type6.clients.test-type.client-secret}")
    private String clientSecret;
    @Value("${type6.clients.test-type.client-redirect-uri}")
    private String redirectUrl;

    private String sessionCookie;
    private String code;
    private String accessToken;
    private String refreshToken;

    @Test
    public void testFlow() {
        step1(); // OAUTH2_AUTHORIZATION_ENDPOINT
        step2(); // LOGIN_ENDPOINT
        step3(); // FORM_LOGIN_ENDPOINT
        step4(); // OAUTH2_AUTHORIZATION_ENDPOINT
        step5(); // OAUTH2_TOKEN_ENDPOINT
        step6(); // TOKEN_INTROSPECTION_ENDPOINT
        step7(); // LOGOUT_ENDPOINT
        step8(); // OAUTH2_JWK_SET_ENDPOINT
    }

    private void step1() {
        var url = origin() + OAUTH2_AUTHORIZATION_ENDPOINT + "?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUrl;
        var response = defaultClient
                .get()
                .uri(url)
                .retrieve();
        var ent = response.toBodilessEntity();
        var headers = ent.getHeaders();

        // updating session cookie
        String cookieLine = headers.get("set-cookie").get(0);
        sessionCookie = cookieLine.substring(cookieLine.indexOf('=') + 1, cookieLine.indexOf(';'));

        String location = headers.get("location").get(0);

        // redirecting to LOGIN_ENDPOINT
        Assertions.assertTrue(location.contains(LOGIN_PAGE));
        Assertions.assertTrue(ent.getStatusCode().is3xxRedirection());
    }

    private void step2() {
        var url = origin() + LOGIN_PAGE;
        var response = defaultClient
                .get()
                .uri(url)
                .header("Cookie", "JSESSIONID=" + sessionCookie)
                .retrieve();
        var ent = response.toEntity(String.class);
        var status = ent.getStatusCode();
        var body = ent.getBody();

        // receiving html login page, it must contains link to FORM_LOGIN_ENDPOINT
        Assertions.assertNotNull(body);
        Assertions.assertTrue(body.contains(FORM_LOGIN_ENDPOINT));
        Assertions.assertTrue(status.is2xxSuccessful());
    }

    private void step3() {
        var url = origin() + FORM_LOGIN_ENDPOINT;
        var response = defaultClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("username=a%40gmail.com&password=a")
                .header("Cookie", "JSESSIONID=" + sessionCookie)
                .retrieve();
        var ent = response.toBodilessEntity();
        var status = ent.getStatusCode();
        var headers = ent.getHeaders();

        // updating session cookie
        String cookieLine = headers.get("set-cookie").get(0);
        sessionCookie = cookieLine.substring(cookieLine.indexOf('=') + 1, cookieLine.indexOf(';'));

        String location = headers.get("location").get(0);

        // redirecting to same url that is on step 1 (OAUTH2_AUTHORIZATION_ENDPOINT)
        Assertions.assertTrue(location.contains(OAUTH2_AUTHORIZATION_ENDPOINT));
        Assertions.assertTrue(status.is3xxRedirection());
    }

    private void step4() {
        var url = origin() + OAUTH2_AUTHORIZATION_ENDPOINT + "?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUrl;
        var response = defaultClient
                .get()
                .uri(url)
                .header("Cookie", "JSESSIONID=" + sessionCookie)
                .retrieve();
        var ent = response.toBodilessEntity();
        var status = ent.getStatusCode();
        var headers = ent.getHeaders();

        String location = headers.get("location").get(0);
        code = location.substring(location.indexOf('=') + 1);

        // redirecting to client redirect url
        Assertions.assertTrue(location.contains(redirectUrl));
        Assertions.assertTrue(status.is3xxRedirection());
    }

    private void step5() {
        var url = origin() + OAUTH2_TOKEN_ENDPOINT;
        var response = defaultClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("grant_type=authorization_code&code=" + code + "&redirect_uri=" + redirectUrl + "&client_id=" + clientId)
                .header("Authorization", "Basic " + new String(Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes())))
                .retrieve();
        var body = response.body(Map.class);
        var ent = response.toBodilessEntity();
        var status = ent.getStatusCode();

        accessToken = body.get("access_token").toString();
        refreshToken = body.get("refresh_token").toString();

        log.info("access: " + accessToken);
        log.info("refresh: " + refreshToken);

        Assertions.assertTrue(status.is2xxSuccessful());
    }

    private void step6() {
        var url = origin() + TOKEN_INTROSPECTION_ENDPOINT;
        var response = defaultClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("token=" + accessToken)
                .header("Authorization", "Basic " + new String(Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes())))
                .retrieve();
        var body = response.body(Map.class);
        var ent = response.toBodilessEntity();
        var status = ent.getStatusCode();

        boolean active = Boolean.parseBoolean(body.get("active").toString());

        Assertions.assertTrue(active);
        Assertions.assertTrue(status.is2xxSuccessful());
    }

    @SneakyThrows
    private void step7() {
        var url = origin() + LOGOUT_ENDPOINT;
        var response = defaultClient
                .get()
                .uri(url)
                .header("Cookie", "JSESSIONID=" + sessionCookie).retrieve();
        var ent = response.toBodilessEntity();
        var status = ent.getStatusCode();
        var headers = ent.getHeaders();

        String location = headers.get("location").get(0);

        log.info(location);
        log.info(redirectUrl);

        // redirecting to redirect url of the client
        Assertions.assertTrue(status.is3xxRedirection());
        Assertions.assertTrue(redirectUrl.contains(location));
    }

    private void step8() {
        var url = origin() + OAUTH2_JWK_SET_ENDPOINT;
        var response = defaultClient
                .get()
                .uri(url).retrieve();
        var body = response.body(Map.class);
        var ent = response.toBodilessEntity();
        var status = ent.getStatusCode();

        Assertions.assertTrue(status.is2xxSuccessful());
        log.info(body.toString());
    }
}
