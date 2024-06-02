package io.mipt.typesix.web.swagger;

import io.swagger.v3.oas.models.PathItem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Type6Endpoint {
    String description() default "";

    PathItem.HttpMethod method();

    ENDPOINT_CATEGORY category() default ENDPOINT_CATEGORY.OTHER;

    enum ENDPOINT_CATEGORY {
        PUBLIC, PAGE, REGISTRATION, THIRD_PARTY, OTHER
    }
}
