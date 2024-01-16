# Type-6

Сервис единой авторизации на протоколе oauth2.
Поддерживает яндекс и гитхаб.

## Параметры для развертывания, которые нужно указать при обновлении конфигурации облака

* KUBERNETES_URL (github action secret)
* KUBERNETES_SECRET (github action secret)

## Переменные окружения, необходимые для запуска микросервиса

### SSL

* TYPE6_ENABLE_SSL

### Клиент

* TYPE6_CLIENT_ID
* TYPE6_CLIENT_REDIRECT_URI
* TYPE6_CLIENT_SECRET
* TYPE6_CLIENT_CORS_ALLOWED_ORIGIN

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

### Прочее

* TYPE6_SELF_IP
* TYPE6_PORT
* TYPE6_ACTIVE_PROFILE
