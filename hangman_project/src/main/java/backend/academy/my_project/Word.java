package backend.academy.my_project;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class Word {
    String name;
    String hint;
    String topic;
    public static final String TOPIC_FOOD = "еда";
    public static final String TOPIC_CITY = "город";
    private static String russianAlphabet = "";
    private Map<String, List<Word>> word = new WeakHashMap<>();
    private static ConsolePrinter consolePrinter = new ConsolePrinter();
    SecureRandom secureRandom = new SecureRandom();

    public Word(String topic, String name, String hint) {
        this.topic = topic;
        this.name = name;
        this.hint = hint;
    }

    public void putWord() {
        word = new HashMap<>();
        word.put(TOPIC_FOOD, createFoodWords());
        word.put(TOPIC_CITY, createCityWords());


    }

    private List<Word> createFoodWords() {
        return Arrays.asList(
            new Word(TOPIC_FOOD, "крекер", "Хрустящее печенье, часто солёное."),
            new Word(TOPIC_FOOD, "кюфта", "Традиционное блюдо из фарша, распространённое на Кавказе."),
            new Word(TOPIC_FOOD, "гаспачо", "Холодный испанский суп из помидоров и овощей."),
            new Word(TOPIC_FOOD, "брускетта", "Итальянская закуска: поджаренный хлеб с различными начинками."),
            new Word(TOPIC_FOOD, "капоната", "Сицилийское блюдо из тушеных овощей, обычно баклажанов."),
            new Word(TOPIC_FOOD, "фриттата", "Итальянский омлет с добавлением сыра, овощей или мяса."),
            new Word(TOPIC_FOOD, "киш", "Открытый французский пирог с начинкой из яиц, сливок и сыра."),
            new Word(TOPIC_FOOD, "медовик", "Традиционный русский торт с мёдом."),
            new Word(TOPIC_FOOD, "рататуй",
                "Французское овощное блюдо, часто из баклажанов, перцев и помидоров."),
            new Word(TOPIC_FOOD, "паппарделле", "Итальянская паста, широкие ленты."),
            new Word(TOPIC_FOOD, "карбонара", "Итальянская паста с соусом из яиц, сыра и бекона."),
            new Word(TOPIC_FOOD, "фондю", "Швейцарское блюдо: расплавленный сыр, в который макают хлеб."),
            new Word(TOPIC_FOOD, "куросо", "Японское сладкое хлебобулочное изделие с кремом."),
            new Word(TOPIC_FOOD, "мезе", "Ассорти из закусок, популярное на Балканах и в Средиземноморье."),
            new Word(TOPIC_FOOD, "табуле", "Ближневосточный салат из булгура, зелени и помидоров."),
            new Word(TOPIC_FOOD, "фалафель", "Жареные шарики из нута, популярные на Ближнем Востоке."),
            new Word(TOPIC_FOOD, "долма", "Виноградные листья, фаршированные мясом и рисом."),
            new Word(TOPIC_FOOD, "тарамасалата", "Греческий дип из икры трески и картофеля или хлеба."),
            new Word(TOPIC_FOOD, "яичница", "Блюдо из жареных яиц."),
            new Word(TOPIC_FOOD, "эскабече", "Испанское блюдо из рыбы или мяса, маринованное и жареное."),
            new Word(TOPIC_FOOD, "аранчини",
                "Сицилийские рисовые шарики с начинкой, покрытые панировкой и обжаренные."),
            new Word(TOPIC_FOOD, "тартин", "Французский сэндвич с открытой начинкой."),
            new Word(TOPIC_FOOD, "кесадилья",
                "Мексиканское блюдо: лепёшка с сыром и другими начинками, обжаренная на сковороде."),
            new Word(TOPIC_FOOD, "тамале",
                ("Мексиканское блюдо из кукурузного теста с "
                    + "начинкой, завернутое в кукурузные листья и приготовленное на пару.")),
            new Word(TOPIC_FOOD, "чилакилес",
                "Традиционное мексиканское блюдо из кукурузных чипсов, политых соусом."),
            new Word(TOPIC_FOOD, "пападум", "Индийская хрустящая лепёшка, часто подается с чатни."),
            new Word(TOPIC_FOOD, "лазанья", "Итальянское блюдо с слоями теста, соуса и сыра."),
            new Word(TOPIC_FOOD, "тжвжик", "Армянское блюдо из субпродуктов, приготовленных с овощами."),
            new Word(TOPIC_FOOD, "дорадо", "Рыба, популярная в средиземноморской кухне."),
            new Word(TOPIC_FOOD, "баклава",
                "Сладкое слоёное тесто с орехами, пропитанное сиропом, популярное на Ближнем Востоке."),
            new Word(TOPIC_FOOD, "катлама", "Традиционное среднеазиатское жареное тесто."),
            new Word(TOPIC_FOOD, "моцарткугель", "Австрийские шоколадные конфеты с марципаном и фисташками."),
            new Word(TOPIC_FOOD, "сациви", "Грузинский соус с орехами, подается к мясу."),
            new Word(TOPIC_FOOD, "мамалыга", "Кукурузная каша, популярная в Румынии и Молдове."),
            new Word(TOPIC_FOOD, "фо", "Вьетнамский суп с рисовой лапшой и мясом."),
            new Word(TOPIC_FOOD, "лапша", "Общее название для видов макаронных изделий в азиатской кухне."),
            new Word(TOPIC_FOOD, "кюльче", "Традиционное азербайджанское сладкое печенье с орехами."),
            new Word(TOPIC_FOOD, "тарталетка", "Маленький открытый пирог с начинкой из фруктов или крема."),
            new Word(TOPIC_FOOD, "калитка",
                "Карельская выпечка из ржаного теста с начинкой из каши или картофеля."),
            new Word(TOPIC_FOOD, "забайоне", "Итальянский десерт из взбитых яиц, сахара и вина."),
            new Word(TOPIC_FOOD, "чизкейк", "Десерт на основе сыра, часто с фруктами или шоколадом."),
            new Word(TOPIC_FOOD, "фугасс", "Прованская лепёшка с травами или оливками.")
        );
    }

    private List<Word> createCityWords() {
        return Arrays.asList(
            new Word(TOPIC_CITY, "Нью-Йорк", "Город в США, известный своим небоскребом Эмпайр-стейт-билдинг."),
            new Word(TOPIC_CITY, "Лондон", "Столица Великобритании, знаменитая своим Биг-Беном."),
            new Word(TOPIC_CITY, "Париж", "Столица Франции, известная Эйфелевой башней."),
            new Word(TOPIC_CITY, "Токио", "Столица Японии, крупнейший мегаполис в мире."),
            new Word(TOPIC_CITY, "Москва", "Столица России, известная Красной площадью и Кремлем."),
            new Word(TOPIC_CITY, "Берлин", "Столица Германии, знаменитая Берлинской стеной."),
            new Word(TOPIC_CITY, "Рим", "Столица Италии, известная Колизеем и Ватиканом."),
            new Word(TOPIC_CITY, "Сидней", "Крупнейший город Австралии, известный Сиднейским оперным театром."),
            new Word(TOPIC_CITY, "Шанхай", "Крупнейший город Китая, важный экономический центр."),
            new Word(TOPIC_CITY, "Барселона", "Город в Испании, известный архитектурой Гауди."),
            new Word(TOPIC_CITY, "Чикаго", "Город в США, известный своими небоскребами и ветрами."),
            new Word(TOPIC_CITY, "Торонто", "Крупнейший город Канады, знаменит своей CN Tower."),
            new Word(TOPIC_CITY, "Мадрид", "Столица Испании, известная Королевским дворцом."),
            new Word(TOPIC_CITY, "Амстердам", "Столица Нидерландов, известная своими каналами и музеями."),
            new Word(TOPIC_CITY, "Бангкок", "Столица Таиланда, известная своими храмами и ночной жизнью."),
            new Word(TOPIC_CITY, "Дубай", "Город в ОАЭ, знаменит небоскребом Бурдж-Халифа."),
            new Word(TOPIC_CITY, "Сан-Франциско", "Город в США, известный мостом Золотые ворота."),
            new Word(TOPIC_CITY, "Кейптаун", "Город в Южной Африке, известный Столовой горой."),
            new Word(TOPIC_CITY, "Сингапур",
                "Город-государство в Юго-Восточной Азии, знаменитый своим ультрасовременным ландшафтом."),
            new Word(TOPIC_CITY, "Афины", "Столица Греции, известная Акрополем и древней историей."),
            new Word(TOPIC_CITY, "Рио-де-Жанейро",
                "Город в Бразилии, знаменитый своей статуей Христа Искупителя."),
            new Word(TOPIC_CITY, "Лос-Анджелес", "Город в США, известный Голливудом и пляжами."),
            new Word(TOPIC_CITY, "Вашингтон", "Столица США, известная Белым домом и Капитолием."),
            new Word(TOPIC_CITY, "Истанбул", "Город в Турции, знаменитый своими мечетями и историей."),
            new Word(TOPIC_CITY, "Мехико", "Столица Мексики, известная своими древними пирамидами."),
            new Word(TOPIC_CITY, "Каир", "Столица Египта, знаменитая пирамидами и Сфинксом."),
            new Word(TOPIC_CITY, "Буэнос-Айрес", "Столица Аргентины, известная танцем танго."),
            new Word(TOPIC_CITY, "Мумбаи", "Крупнейший город Индии, важный финансовый центр."),
            new Word(TOPIC_CITY, "Гонконг", "Город в Китае, известный своими небоскребами и гаванью."),
            new Word(TOPIC_CITY, "Сеул", "Столица Южной Кореи, известная своими технологиями и культурой."),
            new Word(TOPIC_CITY, "Лиссабон", "Столица Португалии, известная своими холмами и трамваями."),
            new Word(TOPIC_CITY, "Прага", "Столица Чехии, знаменитая своим Старым городом и мостами."),
            new Word(TOPIC_CITY, "Вена", "Столица Австрии, известная музыкой и дворцами."),
            new Word(TOPIC_CITY, "Бостон", "Город в США, известный своей историей и университетами."),
            new Word(TOPIC_CITY, "Мельбурн", "Город в Австралии, знаменит культурой и спортом."),
            new Word(TOPIC_CITY, "Хельсинки", "Столица Финляндии, известная своей современной архитектурой."),
            new Word(TOPIC_CITY, "Варшава",
                "Столица Польши, известная историей и восстановлением после Второй мировой войны."),
            new Word(TOPIC_CITY, "Копенгаген", "Столица Дании, известная своим памятником русалочке."),
            new Word(TOPIC_CITY, "Сантьяго", "Столица Чили, расположенная в окружении Анд."),
            new Word(TOPIC_CITY, "Киев", "Столица Украины, известная своими соборами и богатой историей.")
        );
    }

    public Word selectWord(String topic) {
        int number = (secureRandom).nextInt(word.get(topic).size());
        return word.get(topic).get(number);
    }

    public static int letterValid(String letter) {
        if (!String.valueOf(letter.toLowerCase()).matches("[а-я]")) {
            consolePrinter.printlnRed("Данный символ не поддерживается системой");
            return 0;
        } else {
            if (russianAlphabet.contains(letter.toLowerCase())) {
                consolePrinter.printlnRed("Данную букву вы уже пытались вставить в слово");
                return 0;

            } else {
                russianAlphabet += letter;
                return 1;

            }
        }
    }

    public String getName() {
        return name;
    }

    public String getHint() {
        return hint;
    }

    public LinkedList<String> getTopics() {
        if (word == (null) || word.isEmpty()) {
            putWord();
        }
        Set<String> keySet = word.keySet();
        return new LinkedList<>(keySet);
    }

    public String getTopic() {
        return topic;
    }

    public static void setRussianAlphabet(String russianAlphabet) {
        Word.russianAlphabet = russianAlphabet;
    }

    public static String getRussianAlphabet() {
        return russianAlphabet;
    }
}
