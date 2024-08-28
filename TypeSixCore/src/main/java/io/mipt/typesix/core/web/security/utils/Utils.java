package io.mipt.typesix.core.web.security.utils;

import io.mipt.typesix.businesslogic.domain.model.User;
import io.mipt.typesix.core.web.security.form.CustomUserPrinciple;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;

public class Utils {
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String ADMIN_ROLE = ROLE_PREFIX + "admin";

    private Utils() {
    }

    public static String retrieveEmailFromAuthentication(Authentication authentication) {
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

    public static Collection<? extends GrantedAuthority> extractRoles(User user) {
        if (user.getRole() == null) {
            return List.of();
        }
        return List.of(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole()));
    }
}
