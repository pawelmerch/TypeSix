package org.shlimtech.typesix.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.shlimtech.typesix.security.Type6Oauth2ClientProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private static final String DEFAULT_SAVED_REQUEST_SESSION_ATTRIBUTE = "SPRING_SECURITY_SAVED_REQUEST";
    private static final String SAVED_REQUEST_REDIRECT_URL_PARAMETER = "redirect_uri";

    @Value("${spring.security.oauth2.client.registration.yandex.redirect-uri}")
    private String yandexRedirectUrl;
    @Value("${spring.security.oauth2.client.registration.github.redirect-uri}")
    private String githubRedirectUrl;

    private final Type6Oauth2ClientProperties clientProperties;

    @SneakyThrows
    private String getOauth2RedirectUri(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        DefaultSavedRequest savedRequest = (DefaultSavedRequest) session.getAttribute(DEFAULT_SAVED_REQUEST_SESSION_ATTRIBUTE);
        var uri = savedRequest.getParameterValues(SAVED_REQUEST_REDIRECT_URL_PARAMETER);
        return uri == null ? null : uri[0];
    }

    private Type6Oauth2ClientProperties.Type6Oauth2Client getOauth2Client(HttpServletRequest request) {
        String redirectUri = getOauth2RedirectUri(request);

        if (redirectUri == null) {
            return null;
        }

        Optional<Type6Oauth2ClientProperties.Type6Oauth2Client> clientOptional = clientProperties.getClients().values().stream().filter(type6Oauth2Client -> type6Oauth2Client.getClientRedirectUri().equals(redirectUri)).findAny();

        return clientOptional.orElse(null);

    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        Type6Oauth2ClientProperties.Type6Oauth2Client client = getOauth2Client(request);

        if (client == null) {
            return "login";
        }

        return switch (client.getAuthMethod()) {
            case github -> "redirect:" + githubRedirectUrl;
            case yandex -> "redirect:" + yandexRedirectUrl;
            default -> "login";
        };
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Type6Oauth2ClientProperties.Type6Oauth2Client client = getOauth2Client(request);

        if (client == null) {
            throw new RuntimeException("Client not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return makeRedirect(client.getClientHostname());
    }

    private String makeRedirect(String to) {
        return "redirect:" + to;
    }

}
