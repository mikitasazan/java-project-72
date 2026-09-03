# Анализатор страниц (Java)

[![hexlet-check](https://github.com/mikitasazan/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/mikitasazan/java-project-72/actions)
[![Java CI](https://github.com/mikitasazan/java-project-72/actions/workflows/checks.yml/badge.svg)](https://github.com/mikitasazan/java-project-72/actions/workflows/checks.yml)
[![Coverage](.github/badges/jacoco.svg)](https://github.com/mikitasazan/java-project-72/actions/workflows/checks.yml)

Page Analyzer — сайт, который анализирует указанные страницы на SEO-пригодность:
принимает адрес, сохраняет его и по запросу выполняет проверку (код ответа,
`title`, `h1`, meta description).

Учебный проект Хекслета: https://ru.hexlet.io/programs/java
Как это должно работать: https://files.hexlet.app/a/f9wlja

## Стек

- Java 21, Gradle
- Javalin 7 — веб-фреймворк, JTE — шаблонизатор, Tailwind CSS — стили
- JDBC + HikariCP; H2 (разработка и тесты) / PostgreSQL (продакшен)
- Unirest — HTTP-запросы к проверяемым сайтам, jsoup — разбор HTML
- JUnit 5, AssertJ, javalin-testtools, MockWebServer — тесты
- JaCoCo — покрытие тестами

## Установка

```bash
git clone https://github.com/mikitasazan/java-project-72.git
cd java-project-72
make setup
```

`make setup` ставит зависимости для сборки CSS (Tailwind CLI), собирает стили
и делает `./gradlew installDist`.

## Использование

```bash
make start
```

Приложение поднимется на `http://localhost:7070` (порт переопределяется
переменной окружения `PORT`). По умолчанию используется база H2 в памяти;
для подключения к внешней БД (например, PostgreSQL в продакшене) задайте
`JDBC_DATABASE_URL`:

```bash
export JDBC_DATABASE_URL=jdbc:postgresql://host:5432/db?password=pass&user=user
```

Прогнать тесты с покрытием:

```bash
make test
```

Деплой на Render.com пока не выполнен — шаг требует создания отдельного
аккаунта на render.com, что выходит за рамки автоматизированной части этой
задачи.

---

<details>
<summary>Автоматические тесты Хекслета</summary>

Тесты запускаются на каждый коммит. За запуск отвечает файл `.github/workflows/hexlet-check.yml` — не удаляйте и не переименовывайте ни его, ни репозиторий.

</details>

## О Хекслете

[Хекслет](https://ru.hexlet.io/) — школа программирования: авторские программы обучения с практикой, поддержкой наставников и реальными проектами, которые остаются в резюме. Этот репозиторий — один из таких проектов.

