package org.shlimtech.typesix.utils;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.SneakyThrows;
import org.shlimtech.typesix.web.security.form.CustomUserPrinciple;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

public class Utils {
    public static RSAKey generateRsa() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @SneakyThrows
    public static KeyPair generateRsaKey() {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

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