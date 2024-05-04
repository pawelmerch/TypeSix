package org.shlimtech.typesix;

import org.shlimtech.typesixbusinesslogic.EnableTypeSixBusinessLogic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

import static org.shlimtech.typesix.security.EndpointsList.SPRING_DOC_PATH;
import static org.shlimtech.typesix.security.EndpointsList.SWAGGER_UI_BASE_PATH;

@SpringBootApplication
@EnableTypeSixBusinessLogic
public class TypeSixApplication {

    public static void main(String[] args) {
        addCustomProperties(new SpringApplication(TypeSixApplication.class)).run(args);
    }

    private static SpringApplication addCustomProperties(SpringApplication application) {
        Properties properties = new Properties();
        properties.put("springdoc.api-docs.path", SPRING_DOC_PATH);
        properties.put("springdoc.swagger-ui.path", SWAGGER_UI_BASE_PATH);
        application.setDefaultProperties(properties);
        return application;
    }

}
