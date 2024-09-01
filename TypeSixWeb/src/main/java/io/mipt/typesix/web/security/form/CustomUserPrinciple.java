package io.mipt.typesix.web.security.form;

import io.mipt.typesix.businesslogic.domain.model.User;
import io.mipt.typesix.web.security.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserPrinciple implements UserDetails, Serializable {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Utils.extractRoles(user);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

}
