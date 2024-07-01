## Как дебажить и запускать локально

* Устанавливаем [Docker](https://www.docker.com/products/docker-desktop/)
* Устанавливаем [Idea](https://www.jetbrains.com/ru-ru/idea/)
* Устанавливаем [JDK Temurin](https://adoptium.net/temurin/releases/)
* Устанавливаем [maven](https://maven.apache.org/download.cgi)
* Устанавливаем [git](https://git-scm.com/)
* В корне репозитория выполняем команду ```git submodule update --init --remote --recursive```
* В терминале переходим в папку ```scripts/debug``` и выполняем команду ```docker compose up```
* В **Idea** создаем конфигурацию запуска **Spring-boot** и подключаем переменные окружения из файла ```/scripts/debug/.env```

![image](https://github.com/timattt/TypeSix/assets/25401699/14f8dbb4-dc81-4227-a081-65e383c49f82)

![image](https://github.com/timattt/TypeSix/assets/25401699/c7623624-8f17-450a-a35f-b9ac241ab368)

![image](https://github.com/timattt/TypeSix/assets/25401699/268b96c4-ccb4-45d6-be14-bce8ba8eb0f2)

![image](https://github.com/timattt/TypeSix/assets/25401699/6f0f9ac2-22a5-466a-91ba-765a8aa51fdd)

* Запускаем
* В дебажной конфигурации доступно два пользователя с логинами ```a@gmail.com```, ```b@gmail.com``` и паролями ```a```, ```b```
