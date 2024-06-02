package io.mipt.typesix.web.security.oauth2;

import io.micrometer.core.instrument.Counter;
import io.mipt.typesix.businesslogic.service.core.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AuthenticationService authenticationService;
    private final Counter registrationCounter;

    @Autowired
    public CustomOAuth2UserService(AuthenticationService authenticationService, @Qualifier("registration_counter") Counter registrationCounter) {
        this.authenticationService = authenticationService;
        this.registrationCounter = registrationCounter;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        Map<String, Object> attributes = user.getAttributes();
        String email = user.getName();

        boolean created = authenticationService.ensureActiveUserExists(email);

        if (created) {
            registrationCounter.increment();
        }

        authenticationService.complementUserByOauth2ProviderData(email, attributes);

        return user;
    }
}