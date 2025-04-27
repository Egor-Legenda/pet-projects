package backend.academy;

import backend.academy.my_project.mazeGeneration.MazeGeneration;
import lombok.experimental.UtilityClass;


@UtilityClass
public class Main {
    public static MazeGeneration mazeGeneration;

    public static void main(String[] args) {
       mazeGeneration = new MazeGeneration();
       mazeGeneration.start();
    }
}
