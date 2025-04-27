package backend.academy.my_project;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.logging.Logger;
import lombok.Setter;


@Setter
public class Mechanism implements IMechanism {
    private final static Integer LEVEL_MAX = 16;
    public final static int COUNT_LEVEL = 3;
    public final static int COUNT_LEVEL_TOPIC = 2;
    private DifficultyLevel dif = new DifficultyLevel();
    public SecureRandom secureRandom;
    Word word = null;
    Logger logger = Logger.getLogger(Mechanism.class.getName());
    public Word wordsCreator = new Word(null, null, null);
    LinkedList<String> response = new LinkedList<>();
    LinkedList<String> guessWord = new LinkedList<>();
    LinkedList<String> peopleWord = new LinkedList<>();
    Integer len;
    public User user;
    public ConsolePrinter consolePrinter = new ConsolePrinter();
    public ReaderLine reader = new ReaderLine();
    int guessLetter;

    public Mechanism() {
        this.secureRandom = new SecureRandom();
    }

    public Mechanism(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    @Override
    public void start() throws InterruptedException {
        Word.setRussianAlphabet("");
        if (user == null) {
            responseCreate();
            user = registration();
        } else {
            user.setLevel(choiceDifficulty());
            user.setTopic(choiseTopic());
        }
        consolePrinter.printlnRed("* Для повторного просмотра подсказки нажмите любой символ не из русского алфавита");
        word = wordsCreator.selectWord(user.getTopic());
        guessWord = new LinkedList<>();
        peopleWord = new LinkedList<>();
        char[] charArray = word.getName().toCharArray();
        for (char ch : charArray) {
            guessWord.add(Character.toString(ch));
            peopleWord.add("-");
        }
        len = word.getName().length();
        consolePrinter.println("Слово состоит из " + len + " букв ");
        consolePrinter.println("Подсказка: " + word.getHint());
        consolePrinter.print("Слово может состоять только из русских букв. Введите букву: ");
        String letter = "+";
        while (!"-".equals(letter)) {
            String wordNow = "";
            letter = reader.read();
            int m = 0;
            guessLetter = 0;
            int index = 0;
            if ((Word.letterValid(letter)) == 1) {
                for (String c : guessWord) {
                    index += 1;
                    if (c.equalsIgnoreCase(letter) && !c.equalsIgnoreCase(peopleWord.get(index - 1))) {
                        m += 1;
                        peopleWord.set(index - 1, c);
                    }

                    if ((c).equalsIgnoreCase(peopleWord.get(index - 1))) {
                        guessLetter += 1;
                    }
                }
                if (m > 0 && guessLetter == len) {
                    MyLogger.log(user.toString() + " угадал слово " + String.join("", peopleWord)
                        + " у него осталось " + (LEVEL_MAX - DifficultyLevel.getLevelNow()) + " жизней");
                    consolePrinter.println("Вы угадали слово " + String.join("", peopleWord));
                    consolePrinter.println("У вас осталось ещё " + (LEVEL_MAX - DifficultyLevel.getLevelNow()) + "ХП");
                    break;
                } else if (m > 0) {
                    consolePrinter.println("Откройте " + (m) + " букв");
                    consolePrinter.println("Теперь слово выглядит так:");
                    consolePrinter.println(String.join("", peopleWord));
                } else {
                    consolePrinter.println(response.get((secureRandom).nextInt(COUNT_LEVEL)));
                    DifficultyLevel.level();
                    consolePrinter.print("Вы знаете: ");
                    consolePrinter.println(String.join("", peopleWord));
                }
                if (DifficultyLevel.getLevelNow() == LEVEL_MAX) {
                    MyLogger.log(user.toString() + " не угадал слово " + String.join("", guessWord)
                        + " он дошел до " + String.join("", peopleWord)
                        + " у игрока осталось " + (LEVEL_MAX - DifficultyLevel.getLevelNow() + 1) + " ХП");
                    consolePrinter.println("Игра окончена встретимся в следующий раз. Слово было "
                        + String.join("", guessWord));
                    return;
                }
            } else {
                consolePrinter.println("Повторение подсказка: " + word.getHint());
            }
            consolePrinter.print("Введите русскую букву: ");
        }
    }

    public void play() throws InterruptedException {
        String work = "+";
        while (!"-".equals(work)) {
            start();
            do {
                consolePrinter.print("Хотите продолжить(+,-)?");
                work = reader.read().trim();
                if ("-".equals(work)) {
                    consolePrinter.print("Игра окончена");
                }
            } while (!("+".equals(work) || "-".equals(work)));


        }

    }

    public String choiceDifficulty() throws InterruptedException {
        consolePrinter.print("Выберете уровень сложности(легко, нормально, сложно): ");
        String complexity = reader.read().toLowerCase();
        return DifficultyLevel.choice(complexity);
    }

    public String choiseTopic() throws InterruptedException {

        LinkedList<String> topics = wordsCreator.getTopics();
        consolePrinter.print("Выберете тему (" + String.join(", ", topics) + "): ");
        String complexity = reader.read().toLowerCase().trim();
        for (int i = 0; i < topics.size(); i++) {
            if (complexity.equals(topics.get(i))) {
                return complexity.toLowerCase().trim();
            }
        }
        return topics.get((secureRandom).nextInt(COUNT_LEVEL_TOPIC));
    }

    public void responseCreate() {
        response.add("Упс ошибочка");
        response.add("Ничего бывает, нужно ускориться");
        response.add("Жаль, но нужно собраться");
        response.add("Ты был близок");
    }

    public User registration() throws InterruptedException {
        consolePrinter.print("Введите никнейм: ");
        String name;
        name = reader.read();
        return new User(name, choiceDifficulty(), choiseTopic());
    }
}
