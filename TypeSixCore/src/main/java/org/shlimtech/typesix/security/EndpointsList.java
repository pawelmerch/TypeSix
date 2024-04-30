package org.shlimtech.typesix.security;

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
    /**
     * Генерирует токен для клиента в обмен на код
     */
    public static final String OAUTH2_TOKEN_ENDPOINT = PREFIX + "/oauth2/token";
    /**
     * Сюда клиент переходит для начала внешнего конвейера oauth2
     */
    public static final String OAUTH2_AUTHORIZATION_ENDPOINT = PREFIX + "/oauth2/authorize";
    /**
     * Сюда другие микросервисы подключаются, чтобы получить JWK
     */
    public static final String OAUTH2_JWK_SET_ENDPOINT = PREFIX_INTERNAL + "/oauth2/jwks";
    /**
     * Здесь можно проверить валидность токена
     */
    public static final String TOKEN_INTROSPECTION_ENDPOINT = PREFIX + "/oauth2/introspect";
    /**
     * Путь для завершения сессии
     */
    public static final String LOGOUT_ENDPOINT = PREFIX + "/logout";


    //
    // THIRD PARTY OAUTH2 AUTHENTICATING
    //
    /**
     * Прибавляя к этому пути название провайдера, можно начать процесс аутентификации через внешний oauth2
     */
    public static final String THIRD_PARTY_AUTHORIZATION_ENDPOINT = PREFIX + "/oauth2/authorization";
    /**
     * Внешний провайдер по протоколу oauth2 возвращается сюда с кодом
     */
    public static final String THIRD_PARTY_CODE_ENDPOINT = PREFIX + "/login/oauth2/code";


    //
    // REGISTRATION FLOW ENDPOINTS AND PAGES
    //
    public static final String REGISTRATION_EMAIL_ENDPOINT = PREFIX + "/registration/email";
    public static final String REGISTRATION_CODE_ENDPOINT = PREFIX + "/registration/verify";
    public static final String REGISTRATION_PASSWORD_SET_ENDPOINT = PREFIX + "/registration/password";

    public static final String REGISTRATION_EMAIL_PAGE = PREFIX_PAGE + "/registration/email";
    public static final String REGISTRATION_CODE_PAGE = PREFIX_PAGE + "/registration/verify";
    public static final String REGISTRATION_PASSWORD_SET_PAGE = PREFIX_PAGE + "/registration/password";


    //
    // PAGES
    //
    /**
     * Основная страница логина
     */
    public static final String LOGIN_PAGE = PREFIX_PAGE + "/login";
    /**
     * Сюда будет переадресация после успешного логина, при отсутсвии привязки к oauth2-клиенту
     */
    public static final String SUCCESS_LOGIN_PAGE = PREFIX_PAGE + "/success";
    /**
     * Сюда перенаправляются ошибки
     */
    public static final String ERROR_PAGE = PREFIX_PAGE + "/error";


    //
    // OTHER ENDPOINTS
    //
    /**
     * Путь для actuator
     */
    public static final String ACTUATOR_BASE_PATH = PREFIX_INTERNAL + "/actuator";
    /**
     * Сюда клиент отправляет POST-запрос, чтобы указать логин и пароль
     */
    public static final String FORM_LOGIN_ENDPOINT = PREFIX + "/formlogin";
}
