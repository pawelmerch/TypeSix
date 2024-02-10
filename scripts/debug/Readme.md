# Как дебажить и запускать локально

* Устанавливаем [Docker](https://www.docker.com/products/docker-desktop/)
* Устанавливаем [Idea](https://www.jetbrains.com/ru-ru/idea/)
* Устанавливаем [JDK Temurin](https://adoptium.net/temurin/releases/)
* В терминале переходим в папку debug/database и выполняем команду ```docker compose up```
* В Idea создаем конфигурацию запуска Spring-boot и подключаем переменные окружения из файла ```/scripts/debug/.env```
* Запускаем
* В дебажной конфигурации доступно два пользователя с логинами ```a```, ```b```. Пароли совпадают с логинами.