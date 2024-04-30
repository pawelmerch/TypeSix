# Type-6

Сервис единой авторизации и регистрации на протоколе oauth2.
Поддерживает яндекс и гитхаб.

## Для разработчиков

[Как запускать и дебажить](https://github.com/timattt/TypeSix/tree/master/scripts/debug)

## Список эндпоинтов

### Публичное

* OAUTH2_TOKEN_ENDPOINT
* OAUTH2_AUTHORIZATION_ENDPOINT
* OAUTH2_JWK_SET_ENDPOINT
* TOKEN_INTROSPECTION_ENDPOINT
* LOGOUT_ENDPOINT

### Аутентификация через третью сторону

* THIRD_PARTY_AUTHORIZATION_ENDPOINT
* THIRD_PARTY_CODE_ENDPOINT

### Регистрация

* REGISTRATION_EMAIL_ENDPOINT
* REGISTRATION_CODE_ENDPOINT
* REGISTRATION_PASSWORD_SET_ENDPOINT
* REGISTRATION_EMAIL_PAGE
* REGISTRATION_CODE_PAGE
* REGISTRATION_PASSWORD_SET_PAGE

### Страницы

* LOGIN_PAGE
* SUCCESS_LOGIN_PAGE
* ERROR_PAGE

### Прочее

* ACTUATOR_BASE_PATH
* FORM_LOGIN_ENDPOINT

## Сценарии работы

### Двойной oauth2

1. OAUTH2_AUTHORIZATION_ENDPOINT - пользователь переходит сюда, ему создается сессия, в нее кладется redirect URL,
   поскольку пользователь не аутентифицирован в SSO, срабатывает EntryPoint и перенаправляется на страницу логина.
2. LOGIN_PAGE - здесь у пользователя есть кнопка на начала аутентификации через третью сторону.
3. THIRD_PARTY_AUTHORIZATION_ENDPOINT - через этот эндпоинт пользователь попадает на другой сайт для аутентификации.
4. THIRD_PARTY_CODE_ENDPOINT - после успешной аутентификации на другом сайте, пользователь переходит по этому эндпоинту
   с параметром код. И его сразу перенаправляют уже на точку входа клиента вместе с кодом для получения токена уже от
   SSO.
5. OAUTH2_TOKEN_ENDPOINT - уже на клиенте, имея код, пользователь должен обменять этот код на токен.
6. TOKEN_INTROSPECTION_ENDPOINT - а тут пользователь может проверить валидность своего токена.

### Регистрация

### Одинарный auth2

1. OAUTH2_AUTHORIZATION_ENDPOINT - пользователь переходит сюда, ему создается сессия, в нее кладется redirect URL,
   поскольку пользователь не аутентифицирован в SSO, срабатывает EntryPoint и перенаправляется на страницу логина.
2. LOGIN_PAGE - здесь у пользователя есть форма для ввода логина и пароля.
3. FORM_LOGIN_ENDPOINT - после ввода логина и пароля, активируется этот эндпоинт. Если все хорошо, то сессия
   подтверждена и пользователя перенаправляют обратно к себе вместе с кодом.
4. OAUTH2_TOKEN_ENDPOINT - уже на клиенте, имея код, пользователь должен обменять этот код на токен.
5. TOKEN_INTROSPECTION_ENDPOINT - а тут пользователь может проверить валидность своего токена.

## Параметры для развертывания, которые нужно указать при обновлении конфигурации облака

* KUBERNETES_URL (github action secret)
* KUBERNETES_SECRET (github action secret)

## Переменные окружения, необходимые для запуска микросервиса

### Клиенты

#### Type-8

* TYPE8_CLIENT_ID
* TYPE8_CLIENT_REDIRECT_URI
* TYPE8_CLIENT_SECRET
* TYPE8_HOSTNAME

### Github

* TYPE6_GITHUB_CLIENT_ID
* TYPE6_GITHUB_CLIENT_SECRET

### Yandex

* TYPE6_YANDEX_CLIENT_ID
* TYPE6_YANDEX_CLIENT_SECRET

### База данных

* TYPE6_DATABASE_URL
* TYPE6_DATABASE_USER
* TYPE6_DATABASE_PASSWORD

### RabbitMQ

* TYPE6_RABBIT_HOST
* TYPE6_RABBIT_PORT
* TYPE6_RABBIT_USERNAME
* TYPE6_RABBIT_PASSWORD

### Прочее

* TYPE6_SELF_IP
* TYPE6_ACTIVE_PROFILE
* TYPE6_ENABLE_SSL