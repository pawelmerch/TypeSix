package io.mipt.typesix.core.utils;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
public class HttpResponseOutput {
    private String cookie;
    private String location;
    private HttpStatusCode statusCode;
    private String content;
}
