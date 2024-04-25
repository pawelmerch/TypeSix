package org.shlimtech.typesix.security.user;

import lombok.RequiredArgsConstructor;
import org.shlimtech.typesixbusinesslogic.service.core.AuthenticationException;
import org.shlimtech.typesixbusinesslogic.service.core.AuthenticationService;
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
            return new CustomUserPrinciple(authenticationService.loadUser(username), passwordEncoder);
        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("No such user", e);
        }
    }
}
