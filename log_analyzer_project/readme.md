**Краткий гайд по использованию**

Начнем с команд: 
* analyzer - команда, которая анализирует файл или сайт достает из него параметры

Флаги команды(параметры): 

* --path arg - переходит по пути(arg) и построчно анализирует информацию, составляя при этом лист с общей информацией
* --format arg - выводит информацию обработанную в --path в форматах(arg) adoc или markdown
* --from arg - дата будут не раньше чем arg(но учитывается часовой пояс). Arg может бфть как только год, так и год и месяц, так и год и месяц и день
* --to arg - дата будет не позже чем arg
* --filter-field arg - принимает на вход поле по которому будет происходить сравнение в LogFile. Вот все возможные поля
  String ip;
  String name;
  ZonedDateTime time;
  HttpRequest httpRequest;(HttpMethod method;
  String uri;
  HttpVersion version);
  Integer status;
  Integer size;
  String httpRefer; String userAgent;
* --filter-value arg - принимает на вход значение поля(работает только в связке)

Пример использования(это нужно передать, как аргумент командной строки):

    analyzer --path https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs --format adoc --filter-field status --filter-value 200
Команда выполняется на 50% дольше из-за 95 процентиля, поиск которого возможен только через запись всех размеров в лист а потом подсчет 95(оптимизацию этих действий не придумал) 
