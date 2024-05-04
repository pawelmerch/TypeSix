package org.shlimtech.typesix.security;

import org.shlimtech.typesix.swagger.Type6Endpoint;

import static io.swagger.v3.oas.models.PathItem.HttpMethod.GET;
import static io.swagger.v3.oas.models.PathItem.HttpMethod.POST;
import static org.shlimtech.typesix.swagger.Type6Endpoint.ENDPOINT_CATEGORY.*;

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
    public static final String PREFIX = "/sso";
    public static final String PREFIX_INTERNAL = "";
    public static final String PREFIX_PAGE = PREFIX + "/pages";


    //
    // TYPE-N CLIENT AUTHENTICATING VIA OAUTH2
    // PUBLIC API
    //
    @Type6Endpoint(
            description = "Генерирует токен для клиента в обмен на код",
            method = POST,
            category = PUBLIC
    )
    public static final String OAUTH2_TOKEN_ENDPOINT = PREFIX + "/oauth2/token";

    @Type6Endpoint(
            description = "Сюда клиент переходит для начала внешнего конвейера oauth2",
            method = GET,
            category = PUBLIC
    )
    public static final String OAUTH2_AUTHORIZATION_ENDPOINT = PREFIX + "/oauth2/authorize";

    @Type6Endpoint(
            description = "Сюда другие микросервисы подключаются, чтобы получить JWK",
            method = GET,
            category = PUBLIC
    )
    public static final String OAUTH2_JWK_SET_ENDPOINT = PREFIX_INTERNAL + "/oauth2/jwks";

    @Type6Endpoint(
            description = "Здесь можно проверить валидность токена",
            method = POST,
            category = PUBLIC
    )
    public static final String TOKEN_INTROSPECTION_ENDPOINT = PREFIX + "/oauth2/introspect";

    @Type6Endpoint(
            description = "Путь для завершения сессии",
            method = GET,
            category = PUBLIC
    )
    public static final String LOGOUT_ENDPOINT = PREFIX + "/logout";


    //
    // THIRD PARTY OAUTH2 AUTHENTICATING
    //
    @Type6Endpoint(
            description = "Прибавляя к этому пути название провайдера, можно начать процесс аутентификации через внешний oauth2",
            method = GET,
            category = THIRD_PARTY
    )
    public static final String THIRD_PARTY_AUTHORIZATION_ENDPOINT = PREFIX + "/oauth2/authorization";

    @Type6Endpoint(
            description = "Внешний провайдер по протоколу oauth2 возвращается сюда с кодом",
            method = GET,
            category = THIRD_PARTY
    )
    public static final String THIRD_PARTY_CODE_ENDPOINT = PREFIX + "/login/oauth2/code";


    //
    // REGISTRATION FLOW ENDPOINTS AND PAGES
    //
    @Type6Endpoint(description = "Первый шаг конвейера", method = POST, category = REGISTRATION)
    public static final String REGISTRATION_EMAIL_ENDPOINT = PREFIX + "/registration/email";
    @Type6Endpoint(description = "Второй шаг конвейера", method = POST, category = REGISTRATION)
    public static final String REGISTRATION_CODE_ENDPOINT = PREFIX + "/registration/verify";
    @Type6Endpoint(description = "Третий шаг конвейера", method = POST, category = REGISTRATION)
    public static final String REGISTRATION_PASSWORD_SET_ENDPOINT = PREFIX + "/registration/password";

    @Type6Endpoint(description = "Страница для первого шага конвейера", method = GET, category = REGISTRATION)
    public static final String REGISTRATION_EMAIL_PAGE = PREFIX_PAGE + "/registration/email";
    @Type6Endpoint(description = "Страница для второго шага конвейера", method = GET, category = REGISTRATION)
    public static final String REGISTRATION_CODE_PAGE = PREFIX_PAGE + "/registration/verify";
    @Type6Endpoint(description = "Страница для третьего шага конвейера", method = GET, category = REGISTRATION)
    public static final String REGISTRATION_PASSWORD_SET_PAGE = PREFIX_PAGE + "/registration/password";


    //
    // PAGES
    //
    @Type6Endpoint(
            description = "Основная страница логина",
            method = GET,
            category = PAGE
    )
    public static final String LOGIN_PAGE = PREFIX_PAGE + "/login";

    @Type6Endpoint(
            description = "Сюда будет переадресация после успешного логина, при отсутсвии привязки к oauth2-клиенту",
            method = GET,
            category = PAGE
    )
    public static final String SUCCESS_LOGIN_PAGE = PREFIX_PAGE + "/success";

    @Type6Endpoint(
            description = "Сюда перенаправляются ошибки",
            method = GET,
            category = PAGE
    )
    public static final String ERROR_PAGE = PREFIX_PAGE + "/error";


    //
    // OTHER ENDPOINTS
    //
    @Type6Endpoint(
            description = "Путь для actuator",
            method = GET,
            category = OTHER
    )
    public static final String ACTUATOR_BASE_PATH = PREFIX_INTERNAL + "/actuator";

    @Type6Endpoint(
            description = "Сюда клиент отправляет POST-запрос, чтобы указать логин и пароль",
            method = POST,
            category = OTHER
    )
    public static final String FORM_LOGIN_ENDPOINT = PREFIX + "/formlogin";

    @Type6Endpoint(
            description = "Путь для интерфейста SWAGGER",
            method = GET,
            category = OTHER
    )
    public static final String SWAGGER_UI_BASE_PATH = "/docs/swagger-ui";

    @Type6Endpoint(
            description = "Путь для эндпоинта с документацией",
            method = GET,
            category = OTHER
    )
    public static final String SPRING_DOC_PATH = "/docs";

    @Type6Endpoint(description = "test", method = GET, category = OTHER)
    public static final String TEST_ENDPOINT = "/test";
}
