package org.shlimtech.typesix.controller;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shlimtech.typesix.BaseTest;
import org.shlimtech.typesix.utils.HttpRequestInput;
import org.shlimtech.typesix.web.debug.DebugUsersConfig;

import static org.shlimtech.typesix.web.EndpointsList.LOGIN_PAGE;
import static org.shlimtech.typesix.web.EndpointsList.SUCCESS_LOGIN_PAGE;

@Log
public class AuthenticationControllerTests extends BaseTest {
    @Test
    public void successPageWithSessionTest() {
        String cookie = login();
        get(HttpRequestInput.builder().bodyRequested(true).cookie(cookie).build(), SUCCESS_LOGIN_PAGE);
    }

    @Test
    public void successPageWithoutSessionTest() {
        get(HttpRequestInput.builder().bodyRequested(true).build(), SUCCESS_LOGIN_PAGE).getStatusCode().is4xxClientError();
    }

    @Test
    public void loginPageWithCookieTest() {
        String cookie = login();
        var out = get(HttpRequestInput.builder().bodyRequested(true).cookie(cookie).build(), LOGIN_PAGE);

        Assertions.assertTrue(out.getContent().contains(DebugUsersConfig.USER1_EMAIL));
    }

    @Test
    public void loginPageWithoutCookieTest() {
        var out = get(HttpRequestInput.builder().bodyRequested(true).build(), LOGIN_PAGE);

        Assertions.assertFalse(out.getContent().contains(DebugUsersConfig.USER1_EMAIL));
    }
}
