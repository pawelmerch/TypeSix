package io.mipt.typesix.web.security.form;

import io.mipt.typesix.businesslogic.service.core.api.AuthenticationService;
import io.mipt.typesix.businesslogic.service.core.exception.AuthenticationServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthenticationService authenticationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return new CustomUserPrinciple(authenticationService.loginViaForm(username));
        } catch (AuthenticationServiceException e) {
            throw new UsernameNotFoundException("No such user", e);
        }
    }
}
