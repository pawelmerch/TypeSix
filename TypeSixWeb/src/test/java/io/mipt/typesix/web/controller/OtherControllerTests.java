package io.mipt.typesix.web.controller;

import io.mipt.typesix.web.BaseTest;
import io.mipt.typesix.web.utils.HttpRequestInput;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import static io.mipt.typesix.web.EndpointsList.*;

@Log
public class OtherControllerTests extends BaseTest {
    @Test
    public void testActuatorEndpoint() {
        var result = get(HttpRequestInput.builder().bodyRequested(true).build(), ACTUATOR_BASE_PATH + "/prometheus");
        result.getStatusCode().is2xxSuccessful();
        log.info(result.getContent());
        result = get(HttpRequestInput.builder().build(), ACTUATOR_BASE_PATH);
        result.getStatusCode().is2xxSuccessful();
        log.info(result.getContent());
    }

    @Test
    public void testApiDDocsEndpoint() {
        var result = get(HttpRequestInput.builder().build(), SPRING_DOC_PATH);
        result.getStatusCode().is2xxSuccessful();
        log.info(result.getContent());
    }

    @Test
    public void testSwaggerPage() {
        var result = get(HttpRequestInput.builder().build(), SWAGGER_UI_BASE_PATH + "/index.html");
        result.getStatusCode().is2xxSuccessful();
        log.info(result.getContent());
    }
}
