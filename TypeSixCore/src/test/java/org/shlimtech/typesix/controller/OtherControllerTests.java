package org.shlimtech.typesix.controller;

import org.junit.jupiter.api.Test;
import org.shlimtech.typesix.BaseTest;
import org.shlimtech.typesix.utils.HttpRequestInput;

import static org.shlimtech.typesix.web.EndpointsList.*;

public class OtherControllerTests extends BaseTest {
    @Test
    public void testActuatorEndpoint() {
        get(HttpRequestInput.builder().bodyRequested(true).build(), ACTUATOR_BASE_PATH + "/prometheus").getStatusCode().is2xxSuccessful();
        get(HttpRequestInput.builder().build(), ACTUATOR_BASE_PATH).getStatusCode().is2xxSuccessful();
    }

    @Test
    public void testApiDDocsEndpoint() {
        get(HttpRequestInput.builder().build(), SPRING_DOC_PATH).getStatusCode().is2xxSuccessful();
    }

    @Test
    public void testSwaggerPage() {
        get(HttpRequestInput.builder().build(), SWAGGER_UI_BASE_PATH + "/index.html").getStatusCode().is2xxSuccessful();
    }
}
