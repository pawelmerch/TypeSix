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
