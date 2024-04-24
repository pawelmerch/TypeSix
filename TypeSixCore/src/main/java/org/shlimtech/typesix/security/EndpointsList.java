package org.shlimtech.typesix.security;

/**
 * Полный конвейер перехода по эндпоинтам при использовании двойного конвейера протокола oauth2:
 * <br/>
 * 1. OAUTH2_AUTHORIZATION_ENDPOINT
 * <br/>
 * 2. LOGIN_ENDPOINT
 * <br/>
 * 3. THIRD_PARTY_AUTHORIZATION_ENDPOINT
 * <br/>
 * 4. THIRD_PARTY_CODE_ENDPOINT
 * <br/>
 * 5. OAUTH2_TOKEN_ENDPOINT
 * <br/>
 * 6. LOGOUT_ENDPOINT
 * <br/>
 * <br/>
 * Полный конвейер перехода по эндпоинтам при использовании одиночного конвейера протокола oauth2:
 * <br/>
 * 1. OAUTH2_AUTHORIZATION_ENDPOINT
 * <br/>
 * 2. LOGIN_ENDPOINT
 * <br/>
 * 3. FORM_LOGIN_ENDPOINT
 * <br/>
 * 4. OAUTH2_TOKEN_ENDPOINT
 * <br/>
 * 5. LOGOUT_ENDPOINT
 * <br/>
 */
public class EndpointsList {
    public static final String PREFIX = "/sso";


    //
    // TYPE-N CLIENT AUTHENTICATING VIA OAUTH2
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
     * Сюда клиент отправляет POST-запрос, чтобы указать логин и пароль
     */
    public static final String FORM_LOGIN_ENDPOINT = PREFIX + "/formlogin";


    //
    // CUSTOM PAGES FOR TYPE-N CLIENT AUTHENTICATION FLOW
    //
    /**
     * Основная страница логина
     */
    public static final String LOGIN_ENDPOINT = PREFIX + "/login";
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
    // REGISTRATION FLOW ENDPOINTS
    //
    public static final String EMAIL_PAGE = PREFIX + "/email";
    public static final String CODE_PAGE = PREFIX + "/emailcode";
    public static final String PASSWORD_CHANGE_PAGE = PREFIX + "/passwordchange";
    public static final String PASSWORD_SET_ENDPOINT = PREFIX + "/passwordset";


    //
    // OTHER ENDPOINTS
    //
    public static final String ERROR_PAGE = PREFIX + "/error";
    public static final String SUCCESS_LOGIN_PAGE = PREFIX + "/success";
}
