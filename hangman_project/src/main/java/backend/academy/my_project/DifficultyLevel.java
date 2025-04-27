package backend.academy.my_project;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DifficultyLevel {
    private static final int MAX_OUT = 6;
    private static final int NUMBER_EASY = 0;
    private static final int NUMBER_NORMAL = 5;
    private static final int NUMBER_HARD = 9;
    private static int levelNow = 0;
    private static final HashMap<String, Integer> LEVELS = new HashMap<>() { { put(EASY, NUMBER_EASY);
            put(NORMAL, NUMBER_NORMAL);
            put(HARD, NUMBER_HARD);
    }};
    static String complexity;
    static ConsolePrinter consolePrinter = new ConsolePrinter();
    static SecureRandom secureRandom = new SecureRandom();
    static String emptiness = "     ";
    static String stick = "     |";
    static String underscore = "  ____";
    static String head = "  0  |";
    static String body = " /|\\ |";
    static String hanger = "  |  |";
    static String stand = " ____|";
    public static final String EASY = "легко";
    public static final String HARD = "сложно";
    public static final String NORMAL = "нормально";

    private static final LinkedList<LinkedList<String>> LEVEL_OUTPUTS = new LinkedList<>() {{
        add(new LinkedList<>(Arrays.asList(emptiness, emptiness, emptiness, emptiness, emptiness, stand)));
        add(new LinkedList<>(Arrays.asList(emptiness, emptiness, emptiness, emptiness, stick, stand)));
        add(new LinkedList<>(Arrays.asList(emptiness, emptiness, emptiness, stick, stick, stand)));
        add(new LinkedList<>(Arrays.asList(emptiness, emptiness, stick, stick, stick, stand)));
        add(new LinkedList<>(Arrays.asList(emptiness, stick, stick, stick, stick, stand)));
        add(new LinkedList<>(Arrays.asList("     _", stick, stick, stick, stick, stand)));
        add(new LinkedList<>(Arrays.asList("    __", stick, stick, stick, stick, stand)));
        add(new LinkedList<>(Arrays.asList("   ___", stick, stick, stick, stick, stand)));
        add(new LinkedList<>(Arrays.asList(underscore, stick, stick, stick, stick, stand)));
        add(new LinkedList<>(Arrays.asList(underscore, hanger, stick, stick, stick, stand)));
        add(new LinkedList<>(Arrays.asList(underscore, hanger, head, stick, stick, stand)));
        add(new LinkedList<>(Arrays.asList(underscore, hanger, head, hanger, stick, stand)));
        add(new LinkedList<>(Arrays.asList(underscore, hanger, head, " /|  |", stick, stand)));
        add(new LinkedList<>(Arrays.asList(underscore, hanger, head, body, stick, stand)));
        add(new LinkedList<>(Arrays.asList(underscore, hanger, head, body, " /   |", stand)));
        add(new LinkedList<>(Arrays.asList(underscore, hanger, head, body, " / \\ |", stand)));
    }};

    public static void level() {
        for (int i = 0; i < MAX_OUT; i++) {
            consolePrinter.println(LEVEL_OUTPUTS.get(levelNow).get(i));
        }
        levelNow += 1;
    }

    public synchronized static String choice(String complexity) {
        if (LEVELS.containsKey((complexity).trim())) {
            levelNow = LEVELS.get(complexity.toLowerCase().trim());
            return complexity.toLowerCase().trim();
        } else {
            String random;
            random = randomLevel();
            consolePrinter.println("Level был выбран рандомно и равен " + random);
            return random;
        }
    }

    public static String randomLevel() {
        int index = (secureRandom).nextInt(Mechanism.COUNT_LEVEL);
        complexity = getKeyByIndex(LEVELS, index);
        levelNow = LEVELS.get(complexity);
        return complexity;
    }

    public static <K, V> K getKeyByIndex(HashMap<K, V> map, int index) {
        List<K> keys = new ArrayList<>(map.keySet());
        if (index >= 0 && index < keys.size()) {
            return keys.get(index);
        }
        return null;
    }

    protected DifficultyLevel() {
    }

    public static int getLevelNow() {
        return levelNow;
    }

    public static void setLevelNow(int levelNow) {
        DifficultyLevel.levelNow = levelNow;
    }



}
