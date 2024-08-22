# Параметры для развертывания, которые нужно указать при обновлении конфигурации облака

* KUBERNETES_URL (github action secret)
* KUBERNETES_SECRET (github action secret)

# Переменные окружения, необходимые для запуска микросервиса

## Клиенты

#### Type-8

* TYPE8_CLIENT_ID
* TYPE8_CLIENT_REDIRECT_URI
* TYPE8_CLIENT_SECRET
* TYPE8_HOSTNAME

#### Type-12

* TYPE12_CLIENT_ID
* TYPE12_CLIENT_REDIRECT_URI
* TYPE12_CLIENT_SECRET
* TYPE12_HOSTNAME

## Провайдеры pa

#### Github

* TYPE6_GITHUB_CLIENT_ID
* TYPE6_GITHUB_CLIENT_SECRET

#### Yandex

* TYPE6_YANDEX_CLIENT_ID
* TYPE6_YANDEX_CLIENT_SECRET

## Внутренние сервисы

#### Postgres

* TYPE6_POSTGRES_URL
* TYPE6_POSTGRES_USER
* TYPE6_POSTGRES_PASSWORD

#### RabbitMQ

* TYPE6_RABBIT_HOST
* TYPE6_RABBIT_USERNAME
* TYPE6_RABBIT_PASSWORD

## RSA

* TYPE6_WEB_RSA_PUBLIC_KEY_PATH
* TYPE6_WEB_RSA_PRIVATE_KEY_PATH

## Прочее

* TYPE6_HOST
* TYPE6_ACTIVE_PROFILE
* TYPE6_ENABLE_SSL
