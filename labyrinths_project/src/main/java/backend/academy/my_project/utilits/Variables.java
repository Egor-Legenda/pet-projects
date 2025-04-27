package backend.academy.my_project.utilits;

import lombok.Getter;

public enum Variables {
    THRESHOLD_ONE(8),
    THRESHOLD_TWO(9),
    BOUND(10);
    @Getter
    private int variable;

    Variables(int i) {
        this.variable = i;
    }
}
