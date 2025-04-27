package backend.academy.utilits;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Pixel {
    private int red;
    private int green;
    private int blue;
    private int counter;

    public Pixel() {
        red = 0;
        green = 0;
        blue = 0;
        counter = 0;
    }

    public Pixel(int red, int green, int blue, int counter) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.counter = counter;
    }
}
