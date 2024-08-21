package io.mipt.typesix.core.web.controller;

import io.mipt.typesix.core.web.dto.OnlineUserDataDto;
import io.mipt.typesix.core.web.security.oauth2.Type6Oauth2ClientProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

import static io.mipt.typesix.core.utils.Utils.retrieveEmail;
import static io.mipt.typesix.core.web.EndpointsList.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private static final String DEFAULT_SAVED_REQUEST_SESSION_ATTRIBUTE = "SPRING_SECURITY_SAVED_REQUEST";
    private static final String SAVED_REQUEST_REDIRECT_URL_PARAMETER = "redirect_uri";

    private final Type6Oauth2ClientProperties clientProperties;

    @GetMapping(ONLINE_DATA_ENDPOINT)
    public OnlineUserDataDto getOnlineData(HttpServletRequest request) {
        String email = getLoggedUserEmail();
        String lastError = getAuthError(request);

        return OnlineUserDataDto.builder()
                .email(email)
                .lastError(lastError)
                .build();
    }

    @GetMapping(LOGOUT_ENDPOINT)
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String redirect
    ) throws IOException {
        Type6Oauth2ClientProperties.Type6Oauth2Client client = getOauth2Client(request);

        // Invalidating session
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }

        // Making redirect to parameter if parameter exists
        if (redirect != null) {
            response.sendRedirect(redirect);
            return;
        }

        // Use client to determine redirect if no parameter specified
        if (client == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }
        response.sendRedirect(client.getClientHostname());
    }

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

    private String getAuthError(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        AuthenticationException exception = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (exception != null) {
            // Read exception once
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            return exception.getMessage();
        }

        return null;
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
