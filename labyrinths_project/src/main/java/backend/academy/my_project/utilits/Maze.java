package backend.academy.my_project.utilits;

import java.util.List;
import lombok.Getter;
import lombok.Setter;


public class Maze {
    @Getter @Setter
    private List<List<Integer>> grid;
    @Getter @Setter
    private double cost;

    public Maze(List<List<Integer>> grid) {
        this.grid = grid;
    }

    public int getNumber(int x, int y) {
        return grid.get(y).get(x);
    }

    public void setNumber(int x, int y, int number) {
        grid.get(y).set(x, number);
    }

    public int getWidth() {
        return grid.getFirst().size();
    }

    public int getHeight() {
        return grid.size();
    }
}
