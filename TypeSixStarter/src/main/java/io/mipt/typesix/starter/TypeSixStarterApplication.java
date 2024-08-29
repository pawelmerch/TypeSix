package io.mipt.typesix.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

import static io.mipt.typesix.web.EndpointsList.SPRING_DOC_PATH;
import static io.mipt.typesix.web.EndpointsList.SWAGGER_UI_BASE_PATH;

@SpringBootApplication
public class TypeSixStarterApplication {
    public static void main(String[] args) {
        addCustomProperties(new SpringApplication(TypeSixStarterApplication.class)).run(args);
    }

    private static SpringApplication addCustomProperties(SpringApplication application) {
        Properties properties = new Properties();
        properties.put("springdoc.api-docs.path", SPRING_DOC_PATH);
        properties.put("springdoc.swagger-ui.path", SWAGGER_UI_BASE_PATH);
        application.setDefaultProperties(properties);
        return application;
    }
}
