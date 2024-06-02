package io.mipt.typesix.web.swagger;

import io.mipt.typesix.web.EndpointsList;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class OpenAPIConfig {
    private static final String TITLE = "Type-6";

    @Bean
    public OpenAPI customOpenAPI() throws IllegalAccessException {
        OpenAPI openAPI = new OpenAPI();

        openAPI.info(new Info().title(TITLE));

        for (var urlField : EndpointsList.class.getFields()) {
            if (!urlField.isAnnotationPresent(Type6Endpoint.class)) {
                continue;
            }

            var url = urlField.get(null).toString();
            var method = urlField.getDeclaredAnnotation(Type6Endpoint.class).method();
            var description = urlField.getDeclaredAnnotation(Type6Endpoint.class).description();
            var category = StringUtils.capitalize(urlField.getDeclaredAnnotation(Type6Endpoint.class).category().name().toLowerCase().replace("_", " "));

            var operation = new Operation().addTagsItem(category).description(description);

            switch (method) {
                case GET -> openAPI.path(url, new PathItem().get(operation));
                case POST -> openAPI.path(url, new PathItem().post(operation));
            }
        }

        return openAPI;
    }
}
