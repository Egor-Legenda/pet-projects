package backend.academy.utilits;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor @ToString
public class ParameterWithArg {
    @Getter
    private String options;
    @Setter @Getter
    private String arg;

}
