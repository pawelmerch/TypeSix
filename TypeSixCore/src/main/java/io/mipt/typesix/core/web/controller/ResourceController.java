package io.mipt.typesix.core.web.controller;

import io.mipt.typesix.core.web.security.oauth2.Type6Oauth2ClientProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

import static io.mipt.typesix.core.web.EndpointsList.*;

@RestController
@RequiredArgsConstructor
public class ResourceController {
    private static final String FRONTEND_FOLDER = "frontend";
    private static final ClassPathResource INDEX_RESOURCE = new ClassPathResource(FRONTEND_FOLDER + "/index.html");
    private static final String DEFAULT_SAVED_REQUEST_SESSION_ATTRIBUTE = "SPRING_SECURITY_SAVED_REQUEST";
    private static final String SAVED_REQUEST_REDIRECT_URL_PARAMETER = "redirect_uri";

    private final Type6Oauth2ClientProperties clientProperties;

    @GetMapping(value = PREFIX_PUBLIC_PAGE + "/**", produces = MediaType.ALL_VALUE)
    public Resource frontendResource(HttpServletRequest request) {
        var httpPath = request.getServletPath();
        var localPath = httpPath.substring(PREFIX_PUBLIC_PAGE.length());
        var resource = new ClassPathResource(FRONTEND_FOLDER + localPath);

        if (resource.exists()) {
            return resource;
        }

        return INDEX_RESOURCE;
    }

    @GetMapping(value = LOGIN_PAGE, produces = MediaType.ALL_VALUE)
    public Resource loginPage(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        String clientUrl = getOauthClientAuthUrl(request);

        if (clientUrl != null) {
            response.sendRedirect(clientUrl);
        }
        return INDEX_RESOURCE;
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

    private Type6Oauth2ClientProperties.Type6Oauth2Client getOauth2Client(HttpServletRequest request) {
        String redirectUri = getOauth2RedirectUri(request);

        if (redirectUri == null) {
            return null;
        }

        Optional<Type6Oauth2ClientProperties.Type6Oauth2Client> clientOptional = clientProperties.getClients().values().stream().filter(type6Oauth2Client -> type6Oauth2Client.getClientRedirectUri().equals(redirectUri)).findAny();

        return clientOptional.orElse(null);
    }

    private String getOauthClientAuthUrl(HttpServletRequest request) {
        var client = getOauth2Client(request);

        if (client == null || client.getAuthMethod() == Type6Oauth2ClientProperties.AuthMethod.all) {
            return null;
        }

        return THIRD_PARTY_AUTHORIZATION_ENDPOINT + "/" + client.getAuthMethod().toString();
    }

}
