package backend.academy.my_project;

public class Main {

    static Mechanism mechanism = new Mechanism();

    private Main() {
    }

    public static void main(String[] args) throws InterruptedException {
        // запуск механизма
        mechanism.play();
    }
}
