package io.mipt.typesix.web.security.oauth2;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "type6")
public class Type6Oauth2ClientProperties {
    private Map<String, Type6Oauth2Client> clients;

    @Data
    public static class Type6Oauth2Client {
        private String clientId;
        private String clientSecret;
        private String clientRedirectUri;
        private String clientHostname;
        private AuthMethod authMethod;
    }

    public enum AuthMethod {
        yandex, all, github
    }
}
