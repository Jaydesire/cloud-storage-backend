# Дипломная работа “Облачное хранилище”

## Описание проекта

Приложение представляет собой REST-сервис и предназначено для работы пользователя 
с файлами: загрузки, скачивания, переименования и удаления файлов, 
а также получение списка файлов, хранящихся на сервере в настоящий момент. 

Данные пользователей (логин/пароль), а также информация о файлах, находящихся в облачном хранилище хранятся в базе данных MySQL.

Все запросы к сервису авторизованы.
Заранее подготовленное веб-приложение (FRONT) подключается к разработанному сервису без доработок, 
а также использует функционал FRONT для авторизации, загрузки и вывода списка файлов пользователя.
Сервис реализует все методы описанные [yaml файле](./CloudServiceSpecification.yaml).
FRONT доступен на порту 8080, BACKEND на порту 5555.

## Информация о пользователях:

-   USERNAME: root 
    PASSWORD: root

-   USERNAME: elgovsky@yandex.ru 
    PASSWORD: 1234

## Описании реализации:

- Приложение разработано с использованием Spring Boot
- Использован сборщик пакетов maven
- Использована база данных mysql
- Для запуска используется docker, docker-compose
- Код размещен на github
- Код покрыт unit тестами с использованием mockito
- Информация о пользователях и о файлах пользователей хранится в базе данных

## Запуск приложения

## Описание и запуск FRONT:

1. Установить nodejs (рекомендуемая версия 14.16.1, версии выше 16 не поддерживаются) на компьютер следуя инструкции: https://nodejs.org/ru/download/
2. Скачать [FRONT](https://github.com/netology-code/jd-homeworks/tree/master/diploma/netology-diplom-frontend) 
(JavaScript);
3. Перейти в папку FRONT приложения и все команды для запуска выполнять из нее.
4. В файле .env FRONT нужно изменить url до backend: VUE_APP_BASE_URL=http://localhost:5555
5. Следуя описанию README.md FRONT проекта запустить nodejs приложение командой npm install. Появится папка node_modules.
6. Для запуска FRONT приложения с расширенным логированием использовать команду: npm run serve

## Описание и запуск BACKEND:

1. Скачать данный проект, в терминале выполнить команду ./mvnw clean package
2. С помощью docker запусть docker-compose.yml (docker-compose up). После запуска создадутся все необходимые в базе данных таблицы.

## Работа приложения

### 1. Аутентификация
`http://localhost:5555/login`
`POST`
`Content-Type: application/json`

#### *пример:*
`{"login":"root",
"password":"root"
}`

#### *Результаты обработки:*
- предоставление доступа к приложению, успешный ответ с кодом "200" и "auth-token" - токен доступа, 
сформированный для пользователя, по которому будет происходить авторизация к дальнейшим запросам
- неуспешный ответ с кодом "400"(неверные параметры аутентификации)
- неуспешный ответ с кодом "500"(ошибка приложения)

### 2. Загрузка файла
`http://localhost:5555/file?filename=FileName.txt`
`POST`
`Content-Type: multipart/form-data`

#### *Результаты обработки:*
- загрузка файлав хранилище, успешный ответ с кодом "200"
- неуспешный ответ с кодом "500"(ошибка приложения)

### 3. Удаление файла
`http://localhost:5555/file?filename=FileName.txt`
`DELETE`

#### *Результаты обработки:*
- удаление файла из хранилища, успешный ответ с кодом "200"
- неуспешный ответ с кодом "400"(неверные параметры)
- неуспешный ответ с кодом "500"(ошибка приложения)

### 4. Изменение имени файла
`http://localhost:5555/file?filename=FileName.txt`
`PUT`
`Content-Type: application/json`

#### *пример:*
`{"filename":"new_filename.txt"}`

#### *Результаты обработки:*
- изменение имени указанного файла, успешный ответ с кодом "200"
- неуспешный ответ с кодом "400"(неверные параметры)
- неуспешный ответ с кодом "500"(ошибка приложения)

### 5. Скачать файл
`http://localhost:5555/file?filename=FileName.txt`
`GET`

#### *Результаты обработки:*
multipart/form-data контент, успешный ответ с кодом "200"
неуспешный ответ с кодом "400"(неверные параметры)
неуспешный ответ с кодом "500"(ошибка приложения)


### 6. Получить список файлов
`http://localhost:5555/list?limit=3`
`GET`
#### *Результаты обработки:
- список файлов пользователя, успешный ответ с кодом "200"
- неуспешный ответ с кодом "400"(неверные параметры)
- неуспешный ответ с кодом "500"(ошибка приложения)
