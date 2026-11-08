# HeadHunter E2E Tests

Автоматизированные браузерные тесты поиска вакансий на HeadHunter. Проект проверяет поиск с главной страницы, фильтрацию выдачи, расширенный поиск, открытие карточки вакансии и переход к форме отклика или регистрации.

## Технологии

- Java 17.
- Selenium WebDriver 4.25.0.
- JUnit 5, BOM 5.10.0.
- Gradle 8.12 с Gradle Wrapper.
- Chrome и Firefox.
- Page Object для работы со страницами сайта.

## Проверяемые сценарии

- Поиск вакансий с главной страницы и проверка заголовка открытой карточки.
- Боковые фильтры: регион, опыт, образование, занятость, формат работы, доход и график.
- Расширенный поиск и проверка выбранных параметров в URL выдачи.
- Соответствие карточек вакансий выбранным условиям поиска.
- Сквозные сценарии от поиска до перехода к отклику или регистрации.

## Требования

Установите JDK 17 и браузер, в котором будут выполняться тесты. Для запуска в обоих браузерах нужны Chrome и Firefox. Укажите путь к JDK в `JAVA_HOME` и добавьте Java в `PATH`.

Нужен доступ в интернет для загрузки Gradle, зависимостей, драйверов и работы с сайтом. Отдельно устанавливать Gradle не требуется: в проект включён Wrapper.

## Запуск

Выполняйте команды из корня проекта. Примеры приведены для PowerShell в Windows.

Все тесты в Chrome:

```powershell
.\gradlew.bat test
```

Явный выбор браузера:

```powershell
.\gradlew.bat testChrome
.\gradlew.bat testFirefox
```

Параллельный запуск отдельных процессов Gradle для Chrome и Firefox:

```powershell
.\gradlew.bat testBothBrowsers
```

Задача `testBothBrowsers` использует `cmd` и `gradlew.bat`, поэтому рассчитана на Windows. Для отдельных браузеров в Linux и macOS используйте `./gradlew testChrome` или `./gradlew testFirefox`.

Запуск одного тестового класса:

```powershell
.\gradlew.bat testChrome --tests "edu.itmo.hh.search.basic.HhVacancySearchE2ETest"
```

Запуск одного класса в обоих браузерах:

```powershell
.\gradlew.bat testBothBrowsers -PincludeTests=edu.itmo.hh.search.basic.HhVacancySearchE2ETest
```

Чтобы принудительно повторить тесты отдельного браузера, добавьте `--rerun-tasks` к соответствующей команде.

## Настройки браузера

По умолчанию браузер запускается с видимым окном. Для запуска без окна задайте переменную окружения `HEADLESS`:

```powershell
$env:HEADLESS = "true"
.\gradlew.bat testChrome
```

Вернуть запуск с окном:

```powershell
Remove-Item Env:HEADLESS
```

При необходимости укажите пути к драйверам через переменные окружения:

```powershell
$env:CHROME_DRIVER_PATH = "C:\drivers\chromedriver.exe"
$env:GECKO_DRIVER_PATH = "C:\drivers\geckodriver.exe"
```

Фабрика передаёт эти пути WebDriver. Если переменные не заданы, создание драйвера выполняется стандартным механизмом Selenium.

## Структура проекта

```text
src/main/java/edu/itmo/hh/
  config/         Создание WebDriver и типы браузеров
  pages/          Page Object, локаторы и перечисления фильтров
  support/        Выбор браузера для тестовой сессии
src/test/java/edu/itmo/hh/
  AbstractHhWebDriverTest.java   Создание и закрытие браузера
  e2e/main/                     Сквозные сценарии
  search/basic/                 Базовый поиск
  search/filters/advanced/      Расширенный поиск
  search/filters/serp/          Фильтры поисковой выдачи
src/test/resources/
  junit-platform.properties     Параллельное выполнение JUnit
```

JUnit настроен на параллельный запуск тестовых классов. Внутри каждого класса тесты выполняются последовательно с общей браузерной сессией; после завершения класса браузер закрывается.

## Результаты

Gradle выводит статусы тестов и ошибки в консоль. HTML-отчёты создаются в зависимости от выполненной задачи:

- `build/reports/tests/test/index.html`.
- `build/reports/tests/testChrome/index.html`.
- `build/reports/tests/testFirefox/index.html`.

XML-результаты находятся в соответствующих каталогах `build/test-results/`.

Тесты работают с действующим сайтом: результаты зависят от доступности HeadHunter, текущих вакансий, изменений интерфейса и возможных проверок доступа.