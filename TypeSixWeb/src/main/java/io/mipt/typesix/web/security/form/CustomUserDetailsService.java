package io.mipt.typesix.web.security.form;

import io.mipt.typesix.businesslogic.service.core.exception.AuthenticationServiceException;
import io.mipt.typesix.businesslogic.service.core.api.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return new CustomUserPrinciple(authenticationService.loginViaForm(username), passwordEncoder);
        } catch (AuthenticationServiceException e) {
            throw new UsernameNotFoundException("No such user", e);
        }
    }
}
