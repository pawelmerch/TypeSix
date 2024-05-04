package org.shlimtech.typesix.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.shlimtech.typesix.web.security.oauth2.Type6Oauth2ClientProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Optional;

import static org.shlimtech.typesix.utils.Utils.retrieveEmail;
import static org.shlimtech.typesix.web.EndpointsList.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private static final String DEFAULT_SAVED_REQUEST_SESSION_ATTRIBUTE = "SPRING_SECURITY_SAVED_REQUEST";
    private static final String SAVED_REQUEST_REDIRECT_URL_PARAMETER = "redirect_uri";

    private final Type6Oauth2ClientProperties clientProperties;

    @GetMapping(LOGIN_PAGE)
    public String login(HttpServletRequest request, Model model) {
        // Default endpoints
        Arrays.stream(Type6Oauth2ClientProperties.AuthMethod.values()).forEach(provider -> model.addAttribute(provider + "_auth_url", THIRD_PARTY_AUTHORIZATION_ENDPOINT + "/" + provider));
        model.addAttribute("form_login_endpoint", FORM_LOGIN_ENDPOINT);
        model.addAttribute("email_page", REGISTRATION_EMAIL_PAGE);
        model.addAttribute("logout_endpoint", LOGOUT_ENDPOINT);

        // User email
        String email = getLoggedUserEmail();
        model.addAttribute("isLogged", email != null);
        if (email != null) {
            model.addAttribute("userEmail", email);
        }

        // User oauth2 client
        Type6Oauth2ClientProperties.Type6Oauth2Client client = getOauth2Client(request);
        if (client == null) {
            model.addAttribute("clientMessage", "You are not bounded to any oauth2-client");
            return "login";
        }

        model.addAttribute("clientMessage", "You came from a client: " + client.getClientId());

        if (client.getAuthMethod() == Type6Oauth2ClientProperties.AuthMethod.all) {
            return "login";
        }

        return makeRedirect(THIRD_PARTY_AUTHORIZATION_ENDPOINT + "/" + client.getAuthMethod().toString());
    }

    @GetMapping(LOGOUT_ENDPOINT)
    public String logout(HttpServletRequest request, @RequestParam(required = false) String redirect) {
        Type6Oauth2ClientProperties.Type6Oauth2Client client = getOauth2Client(request);

        // Invalidating session
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }

        // Making redirect to parameter
        if (redirect != null) {
            return makeRedirect(redirect);
        }

        // Use client to determine redirect
        if (client == null) {
            return makeRedirect(LOGIN_PAGE);
        }
        return makeRedirect(client.getClientHostname());
    }

    @GetMapping(SUCCESS_LOGIN_PAGE)
    public String success() {
        return "success";
    }

    @ExceptionHandler
    public String errorHandler(Exception exception) {
        return "redirect:" + ERROR_PAGE + "?message=Unknown exception: " + exception.getMessage();
    }

    private String makeRedirect(String to) {
        return "redirect:" + to;
    }

    @SneakyThrows
    private String getOauth2RedirectUri(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        DefaultSavedRequest savedRequest = (DefaultSavedRequest) session.getAttribute(DEFAULT_SAVED_REQUEST_SESSION_ATTRIBUTE);
        if (savedRequest == null) {
            return null;
        }
        var uri = savedRequest.getParameterValues(SAVED_REQUEST_REDIRECT_URL_PARAMETER);
        return uri == null ? null : uri[0];
    }

    private String getLoggedUserEmail() {
        try {
            return retrieveEmail(SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception ignored) {
            return null;
        }
    }

    private Type6Oauth2ClientProperties.Type6Oauth2Client getOauth2Client(HttpServletRequest request) {
        String redirectUri = getOauth2RedirectUri(request);

        if (redirectUri == null) {
            return null;
        }

        Optional<Type6Oauth2ClientProperties.Type6Oauth2Client> clientOptional = clientProperties.getClients().values().stream().filter(type6Oauth2Client -> type6Oauth2Client.getClientRedirectUri().equals(redirectUri)).findAny();

        return clientOptional.orElse(null);
    }

}
