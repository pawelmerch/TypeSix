# Локальная отладка

## Запуск инфраструктуры в докере

```bash
docker compose --profile local -p type-6-infrastructure up --build
```

## Запуск всей системы в докере

```bash
docker compose --profile dockerized -p type-6-dockerized up --build
```