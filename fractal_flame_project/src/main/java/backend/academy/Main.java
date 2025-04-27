package backend.academy;

import backend.academy.generator.GeneratorFractal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        GeneratorFractal generatorFractal = new GeneratorFractal();
        generatorFractal.generate();
    }
}
