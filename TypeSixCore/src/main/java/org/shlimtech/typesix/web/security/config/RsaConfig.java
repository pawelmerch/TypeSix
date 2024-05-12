package org.shlimtech.typesix.web.security.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

@Configuration
public class RsaConfig {
    @Bean
    public JWKSource<SecurityContext> jwkSource(@Value("${type-6.rsa.public-key-file-path}") Path publicKeyPath,
                                                @Value("${type-6.rsa.private-key-file-path}") Path privateKeyPath) {
        RSAKey rsaKey = loadRsa(publicKeyPath, privateKeyPath);
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private RSAKey loadRsa(Path publicKeyPath, Path privateKeyPath) {
        KeyPair keyPair = new KeyPair(loadPublicKey(publicKeyPath), loadPrivateKey(privateKeyPath));
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @SneakyThrows
    public static PublicKey loadPublicKey(Path path) {
        byte[] keyBytes = Files.readAllBytes(path);

        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    @SneakyThrows
    public static PrivateKey loadPrivateKey(Path path) {
        byte[] keyBytes = Files.readAllBytes(path);

        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
