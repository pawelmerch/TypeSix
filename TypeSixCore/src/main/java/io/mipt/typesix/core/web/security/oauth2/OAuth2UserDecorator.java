package io.mipt.typesix.core.web.security.oauth2;

import io.mipt.typesix.businesslogic.domain.model.User;
import io.mipt.typesix.core.web.security.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class OAuth2UserDecorator implements OAuth2User {
    private final OAuth2User delegate;
    private final User user;

    @Override
    public Map<String, Object> getAttributes() {
        return delegate.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Utils.extractRoles(user);
    }

    @Override
    public String getName() {
        return delegate.getName();
    }
}
