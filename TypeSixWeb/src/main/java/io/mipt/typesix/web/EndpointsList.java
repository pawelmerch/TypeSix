package io.mipt.typesix.web;

import static io.mipt.typesix.web.swagger.Type6Endpoint.ENDPOINT_CATEGORY.*;
import static io.swagger.v3.oas.models.PathItem.HttpMethod.GET;
import static io.swagger.v3.oas.models.PathItem.HttpMethod.POST;

/**
 * Полный конвейер перехода по эндпоинтам при использовании двойного конвейера протокола oauth2:
 * <br/>
 * 1. OAUTH2_AUTHORIZATION_ENDPOINT
 * <br/>
 * 2. LOGIN_PAGE
 * <br/>
 * 3. THIRD_PARTY_AUTHORIZATION_ENDPOINT
 * <br/>
 * 4. THIRD_PARTY_CODE_ENDPOINT
 * <br/>
 * 5. OAUTH2_TOKEN_ENDPOINT
 * <br/>
 * 6. TOKEN_INTROSPECTION_ENDPOINT
 * <br/>
 * 7. LOGOUT_ENDPOINT
 * <br/>
 * <br/>
 * Полный конвейер перехода по эндпоинтам при использовании одиночного конвейера протокола oauth2:
 * <br/>
 * 1. OAUTH2_AUTHORIZATION_ENDPOINT
 * <br/>
 * 2. LOGIN_PAGE
 * <br/>
 * 3. FORM_LOGIN_ENDPOINT
 * <br/>
 * 4. OAUTH2_TOKEN_ENDPOINT
 * <br/>
 * 5. TOKEN_INTROSPECTION_ENDPOINT
 * <br/>
 * 6. LOGOUT_ENDPOINT
 * <br/>
 */
public class EndpointsList {
    public static final String PREFIX_PUBLIC = "/public";
    public static final String PREFIX_INTERNAL = "";
    public static final String PREFIX_PUBLIC_PAGE = PREFIX_PUBLIC + "/pages";
    public static final String PREFIX_PUBLIC_ADMIN = PREFIX_PUBLIC + "/admin";


    //
    // TYPE-N CLIENT AUTHENTICATING VIA OAUTH2
    // PUBLIC API
    //
    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Генерирует токен для клиента в обмен на код",
            method = POST,
            category = PUBLIC
    )
    public static final String OAUTH2_TOKEN_ENDPOINT = PREFIX_PUBLIC + "/oauth2/token";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Сюда клиент переходит для начала внешнего конвейера oauth2",
            method = GET,
            category = PUBLIC
    )
    public static final String OAUTH2_AUTHORIZATION_ENDPOINT = PREFIX_PUBLIC + "/oauth2/authorize";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Сюда другие микросервисы подключаются, чтобы получить JWK",
            method = GET,
            category = PUBLIC
    )
    public static final String OAUTH2_JWK_SET_ENDPOINT = PREFIX_INTERNAL + "/oauth2/jwks";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Здесь можно проверить валидность токена",
            method = POST,
            category = PUBLIC
    )
    public static final String TOKEN_INTROSPECTION_ENDPOINT = PREFIX_PUBLIC + "/oauth2/introspect";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Путь для завершения сессии",
            method = GET,
            category = PUBLIC
    )
    public static final String LOGOUT_ENDPOINT = PREFIX_PUBLIC + "/logout";


    //
    // THIRD PARTY OAUTH2 AUTHENTICATING
    //
    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Прибавляя к этому пути название провайдера, можно начать процесс аутентификации через внешний oauth2",
            method = GET,
            category = THIRD_PARTY
    )
    public static final String THIRD_PARTY_AUTHORIZATION_ENDPOINT = PREFIX_PUBLIC + "/oauth2/authorization";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Внешний провайдер по протоколу oauth2 возвращается сюда с кодом",
            method = GET,
            category = THIRD_PARTY
    )
    public static final String THIRD_PARTY_CODE_ENDPOINT = PREFIX_PUBLIC + "/login/oauth2/code";


    //
    // REGISTRATION FLOW ENDPOINTS AND PAGES
    //
    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Первый шаг конвейера",
            method = POST,
            category = REGISTRATION
    )
    public static final String REGISTRATION_EMAIL_ENDPOINT = PREFIX_PUBLIC + "/registration/email";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Второй шаг конвейера",
            method = POST,
            category = REGISTRATION
    )
    public static final String REGISTRATION_CODE_ENDPOINT = PREFIX_PUBLIC + "/registration/verify";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Третий шаг конвейера",
            method = POST,
            category = REGISTRATION
    )
    public static final String REGISTRATION_PASSWORD_SET_ENDPOINT = PREFIX_PUBLIC + "/registration/password";


    //
    // PAGES
    //
    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Основная страница логина",
            method = GET,
            category = PAGE
    )
    public static final String LOGIN_PAGE = PREFIX_PUBLIC_PAGE + "/login";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Сюда будет переадресация после успешного логина, при отсутсвии привязки к oauth2-клиенту",
            method = GET,
            category = PAGE
    )
    public static final String SUCCESS_LOGIN_PAGE = PREFIX_PUBLIC_PAGE + "/success";


    //
    // OTHER ENDPOINTS
    //
    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Путь для actuator",
            method = GET,
            category = OTHER
    )
    public static final String ACTUATOR_BASE_PATH = PREFIX_INTERNAL + "/actuator";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Сюда клиент отправляет POST-запрос, чтобы указать логин и пароль",
            method = POST,
            category = OTHER
    )
    public static final String FORM_LOGIN_ENDPOINT = PREFIX_PUBLIC + "/formlogin";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Путь для интерфейста SWAGGER",
            method = GET,
            category = OTHER
    )
    public static final String SWAGGER_UI_BASE_PATH = PREFIX_PUBLIC + "/docs/swagger-ui";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Путь для эндпоинта с документацией",
            method = GET,
            category = OTHER
    )
    public static final String SPRING_DOC_PATH = PREFIX_PUBLIC + "/docs";


    //
    // ADMIN
    //
    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Позволяет узнать, есть ли у зарегистрированного пользователя роль администратора",
            method = GET,
            category = ADMIN
    )
    public static final String IS_ADMIN_ENDPOINT = PREFIX_PUBLIC_ADMIN + "/check";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Позволяет получить список пользователей и ролей к ним",
            method = GET,
            category = ADMIN
    )
    public static final String ADMIN_ALL_USERS_LIST_ENDPOINT = PREFIX_PUBLIC_ADMIN + "/users";

    @io.mipt.typesix.web.swagger.Type6Endpoint(
            description = "Устанавливает роль для пользователя",
            method = POST,
            category = ADMIN
    )
    public static final String ADMIN_SET_ROLE_ENDPOINT = PREFIX_PUBLIC_ADMIN + "/users/{id}/role";
}
