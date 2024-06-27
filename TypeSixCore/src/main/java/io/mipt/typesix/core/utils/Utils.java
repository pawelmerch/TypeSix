package io.mipt.typesix.core.utils;

import io.mipt.typesix.core.web.security.form.CustomUserPrinciple;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

@Log
public class Utils {
    public static String retrieveEmail(Authentication authentication) {
        Assert.notNull(authentication, "authentication must not be null");
        if (authentication instanceof OAuth2AuthenticationToken token) {
            OAuth2User user = token.getPrincipal();
            String email = user.getName();
            return email;
        } else if (authentication instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
            CustomUserPrinciple userPrinciple = (CustomUserPrinciple) usernamePasswordAuthenticationToken.getPrincipal();
            return userPrinciple.getUsername();
        }

        throw new IllegalStateException("Unsupported authentication type: " + authentication.getClass());
    }
}