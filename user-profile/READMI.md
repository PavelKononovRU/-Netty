<h2>В данный момент основной модуль - все классы и пакеты создаем в этом модуле</h2>
<h3>Добавление Liquibase для создания таблицы модели </h3>
- Добавление зависимости(зависимость уже добавлена).
- Создание в resources папки changelog и добавление файла db.changelog-master.yaml(сделано).
- в db.changelog-master.yaml добавляем путь к скрипту который будет отвечать за создание таблицы.
- создаем сам скрипт, название должно соответствовать тому что он делает(create-tariff-table.yaml смотрим пример).
- запускаем, таблица вашей модели создана