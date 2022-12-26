# Дипломный проект профессии «Тестировщик ПО»

[План автоматизации тестирования веб-формы сервиса покупки туров](https://github.com/century90/diplom_work_qa43/blob/master/Documents/plan.md)

[Отчёт по итогам тестирования](https://github.com/century90/diplom_work_qa43/blob/master/Documents/Report.md)

[Отчёт о проведённой автоматизации](https://github.com/century90/diplom_work_qa43/blob/master/Documents/Summary.md)

# *Инструкция по запуску:*

1. Склонировать репозиторий:

       git clone https://github.com/century90/diplom_work_qa43.git

2. Запустить контейнеры docker:

       docker-compose up

3. В новой вкладке терминала ввести следующую команду в зависимости от базы данных:

*для MySQL:*

    java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar

*для PostreSQL:*

    java -Dspring.datasource-postgresql.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar

4. Проверить запуск приложения по адресу:

http://localhost:8080/

5. В новой вкладке терминала ввести команду в зависимости от запущенной БД в п.3 данной Инструкции:

*для MySQL:*

    ./gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app

*для PostreSQL:*

    ./gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app

6. Если необходимо перезапустить приложение и/или тесты (например, для другой БД), необходимо выполнить остановку работы в запущенных ранее вкладках терминала, нажав в них Ctrl+С

7. Сформировать отчет AllureReport по результатам тестирования:

       ./gradlew allureServe

8. Сгенерированный отчет откроется в браузере автоматически. После просмотра и закрытия отчета можно остановить работу команды, нажав Ctrl+С или закрыть вкладку Run и нажать Disconnect

