# Автотесты для проверки поиска на Википедии

Набор автотестов для проверки функциональности поиска на Википедии, соответствующий бизнес-требованиям.

## Технологии
- Java 23
- Selenide
- JUnit 5
- Allure Report
- Maven

## Структура проекта
````
└── test
     └── java
        └── wiki
            ├── configurations
            │   └── ConfigTest
            ├── exceptions
            │   └── SkipException
            ├── pages
            │   ├── MainPage
            │   └── SearchResultsPage
            └── tests
                ├── BaseTest
                ├── SearchNavigationTest
                └── SearchSuggestionsTest
````



## Описание тестов
### SearchNavigationTest
* Проверка точного перехода по первому саджесту

* Проверка перехода по кнопке поиска

* Проверка перехода на страницу поиска при отсутствии саджестов

* Проверка отображения подсказки "Поиск страниц содержащих"

### SearchSuggestionsTest
* Проверка форматирования саджестов (начало с запроса и визуальное выделение)

## Особенности реализации
* Использован паттерн PageObject

* Тесты готовы к параллельному запуску

* Неавтоматизированные тесты помечены как пропущенные с пояснением причины

## Запуск всех тестов
    mvn clean test

## Запуск конкретного тестового класса
    mvn test -Dtest=wiki.tests.SearchNavigationTest

## Запуск одного теста
    mvn test -Dtest=wiki.tests.SearchNavigationTest#navigateFromFirstSuggestion

## Запуск группы тестов
    mvn test -Dtest="wiki.tests.Search*Test"