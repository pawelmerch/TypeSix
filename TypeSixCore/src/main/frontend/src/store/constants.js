const PREFIX_PUBLIC = "/public"
const PREFIX_PUBLIC_PAGE = PREFIX_PUBLIC + "/pages"

export const LOGOUT_ENDPOINT = PREFIX_PUBLIC + "/logout"
export const FORM_LOGIN_ENDPOINT = PREFIX_PUBLIC + "/formlogin"

export const LOGIN_PAGE = PREFIX_PUBLIC_PAGE + "/login"
export const SUCCESS_LOGIN_PAGE = PREFIX_PUBLIC_PAGE + "/success"
export const ERROR_PAGE = PREFIX_PUBLIC_PAGE + "/error"
export const UNAUTHORIZED_PAGE = PREFIX_PUBLIC_PAGE + "/unauthorized"

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