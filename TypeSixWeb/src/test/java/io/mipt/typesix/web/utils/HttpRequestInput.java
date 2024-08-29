package io.mipt.typesix.web.utils;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class HttpRequestInput {
    private String cookie;
    private Map<String, String> mime;
    private String auth;
    private boolean bodyRequested;

    public boolean hasCookie() {
        return cookie != null && !cookie.isEmpty();
    }

    public boolean hasMime() {
        return mime != null && !mime.isEmpty();
    }

    public boolean hasAuth() {
        return auth != null && !auth.isEmpty();
    }
}
