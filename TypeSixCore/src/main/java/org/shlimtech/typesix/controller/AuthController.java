package org.shlimtech.typesix.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.shlimtech.typesix.security.Type6Oauth2ClientProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Optional;

import static org.shlimtech.typesix.security.EndpointsList.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    public static final String DEFAULT_SAVED_REQUEST_SESSION_ATTRIBUTE = "SPRING_SECURITY_SAVED_REQUEST";
    public static final String SAVED_REQUEST_REDIRECT_URL_PARAMETER = "redirect_uri";

    public static final String yandexAuthUrl = THIRD_PARTY_AUTHORIZATION_ENDPOINT + "/yandex";
    public static final String githubAuthUrl = THIRD_PARTY_AUTHORIZATION_ENDPOINT + "/github";

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

    @GetMapping(LOGIN_ENDPOINT)
    public String login(HttpServletRequest request, Model model) {
        Type6Oauth2ClientProperties.Type6Oauth2Client client = getOauth2Client(request);
        
        Arrays.stream(Type6Oauth2ClientProperties.AuthMethod.values()).forEach(provider -> model.addAttribute(provider + "_auth_url", THIRD_PARTY_AUTHORIZATION_ENDPOINT + "/" + provider));

        if (client == null) {
            return "login";
        }

        return switch (client.getAuthMethod()) {
            case github -> makeRedirect(githubAuthUrl);
            case yandex -> makeRedirect(yandexAuthUrl);
            default -> "login";
        };
    }

    @GetMapping(LOGOUT_ENDPOINT)
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
