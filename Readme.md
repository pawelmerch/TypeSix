# Type-6

Сервис единой авторизации на протоколе oauth2.
Поддерживает яндекс и гитхаб.

## Для разработчиков

[Как запускать и дебажить](https://github.com/timattt/TypeSix/tree/master/scripts/debug)

## Параметры для развертывания, которые нужно указать при обновлении конфигурации облака

* KUBERNETES_URL (github action secret)
* KUBERNETES_SECRET (github action secret)

## Переменные окружения, необходимые для запуска микросервиса

### SSL

* TYPE6_ENABLE_SSL

### Клиент

* TYPE7_CLIENT_ID
* TYPE7_CLIENT_REDIRECT_URI
* TYPE7_CLIENT_SECRET
* TYPE7_HOSTNAME

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
