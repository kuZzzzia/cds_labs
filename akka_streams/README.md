# cds_lab_5_akka_streams

## Задание

Требуется разработать приложение использующее технологию akka streams и позволяющее с помощью http запроса несколько одинаковых GET запросов и померять среднее время отклика.
Пример запроса : ``http://localhost:8080/?testUrl=http://rambler.ru&count=20``