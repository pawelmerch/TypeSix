const PREFIX_PUBLIC = "/public"
const PREFIX_PUBLIC_PAGE = PREFIX_PUBLIC + "/pages"
const PREFIX_PUBLIC_ADMIN = PREFIX_PUBLIC + "/admin";

export const LOGOUT_ENDPOINT = PREFIX_PUBLIC + "/logout"
export const FORM_LOGIN_ENDPOINT = PREFIX_PUBLIC + "/formlogin"

export const LOGIN_PAGE = PREFIX_PUBLIC_PAGE + "/login"
export const SUCCESS_LOGIN_PAGE = PREFIX_PUBLIC_PAGE + "/success"
export const ERROR_PAGE = PREFIX_PUBLIC_PAGE + "/error"
export const UNAUTHORIZED_PAGE = PREFIX_PUBLIC_PAGE + "/unauthorized"
export const ADMIN_ROLE_SET_PAGE = PREFIX_PUBLIC_PAGE + "/admin"

export const REGISTRATION_PAGE = PREFIX_PUBLIC_PAGE + "/registration"
export const THIRD_PARTY_AUTHORIZATION_ENDPOINT = PREFIX_PUBLIC + "/oauth2/authorization"

export const REGISTRATION_EMAIL_ENDPOINT = PREFIX_PUBLIC + "/registration/email"
export const REGISTRATION_CODE_ENDPOINT = PREFIX_PUBLIC + "/registration/verify"
export const REGISTRATION_PASSWORD_SET_ENDPOINT = PREFIX_PUBLIC + "/registration/password"

export const PROVIDER_GITHUB = THIRD_PARTY_AUTHORIZATION_ENDPOINT + "/github"
export const PROVIDER_YANDEX = THIRD_PARTY_AUTHORIZATION_ENDPOINT + "/yandex"

export const PROVIDERS = [
    {name: "github", url: PROVIDER_GITHUB},
    {name: "yandex", url: PROVIDER_YANDEX}
]

export const IS_ADMIN_ENDPOINT = PREFIX_PUBLIC_ADMIN + "/check";
export const ADMIN_ALL_USERS_LIST_ENDPOINT = PREFIX_PUBLIC_ADMIN + "/users";
export const ADMIN_SET_ROLE_ENDPOINT = PREFIX_PUBLIC_ADMIN + "/users/{id}/role";

export const ROLES = ["admin", "user"]
export const NO_ROLE = ""
export const USERS_PER_LOAD = 2;