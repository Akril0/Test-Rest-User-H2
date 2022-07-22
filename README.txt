
Тестируем REST API
----------------------

Используем Postman (инструмент
тестирования REST API)
https://www.postman.com/

Запустим код в IDE.

1) REST-запрос на получение данных.
Но получим пустой массив, поскольку
данные еще не вносились

GET http://localhost:8080/api/users


2) REST-запрос на создание данных

POST http://localhost:8080/api/users

Настройки в Postman: Body, raw, JSON.
Прописываем в отправке каждый раз отдельно



    {
        "name": "Alice",
        "email": "alicemarko200@gmail.com",
        "phone": "555 123-4587"
    }

    {
        "name": "Lucy",
        "email": "lucymarko200@gmail.com",
        "phone": "555 111-9944"
    }

    {
        "name": "Jessica",
        "email": "alicemarko200@gmail.com",
        "phone": "555 777-1234"
    }

    {
        "name": "Robert",
        "email": "alicemarko200@gmail.com",
        "phone": "555 012-7833"
    }


3) REST-запрос на получение данных по id

GET http://localhost:8080/api/users/2


4) REST-запрос на обновление данных по id

PUT http://localhost:8080/api/users/5

{
    "name": "Lucy",
    "email": "lucymarko200@gmail.com",
	"phone": "555 999-3333",
}


5) REST-запрос на удаление данных по id

DELETE http://localhost:8080/api/users/4




