# Автотесты для VK Маркета

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://java.com)
[![Selenide](https://img.shields.io/badge/Selenide-6.19.1-green)](https://selenide.org)
[![JUnit5](https://img.shields.io/badge/JUnit-5-yellow)](https://junit.org)

## О проекте

Набор автотестов для проверки функциональности:
- Карточки товара в VK Маркете
- Главной страницы сообщества

### Технологии
- **Java 17** - основной язык
- **Selenide** - фреймворк для UI-тестирования
- **JUnit 5** - тестовая среда
- **Page Object Pattern** - организация кода

## Что проверяет

### Тесты карточки товара
✔ Отображение основных элементов (изображение, цена, описание)  
✔ Работа кнопок:
- "Написать"
- "Поделиться"
- "Подписаться"
- "Перейти в магазин"
- "Добавить в желания" 

✔ Функциональность вкладки комментариев

### Тесты сообщества
✔ Название сообщества  
✔ Кнопка подписки  
✔ Вкладка "Товары"  
✔ Информация о сообществе

## Как запустить

gradlew test


## Структура проекта 
```aiignore
src/   
└── test/
    └── java/
        ├── config/
        │    └── ConfigTest
        ├── pages/
        │   ├── CommunityPage
        │   └── ProductPage
        └── tests/     
            ├── CommunityPageTest
            └── ProductPageTest
```
