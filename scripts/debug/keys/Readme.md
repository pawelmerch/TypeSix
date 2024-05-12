# Генерации RSA-пары

### Генерация самого ключа

```
openssl genrsa -out private_key.pem 2048
```

### Конвертируем в читаемый для java формат

```
openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt
```

```
openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der
```