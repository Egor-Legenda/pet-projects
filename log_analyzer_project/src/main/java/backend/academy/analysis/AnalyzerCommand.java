package backend.academy.analysis;

import backend.academy.command.Command;
import backend.academy.options.FilterParameter;
import backend.academy.options.FormatParameter;
import backend.academy.options.FromParameter;
import backend.academy.options.Parameter;
import backend.academy.options.PathParameter;
import backend.academy.options.ToParameter;
import backend.academy.utilits.ParameterWithArg;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AnalyzerCommand implements Command {

    @Override
    public void doCommand(List<ParameterWithArg> parameterWithArgs) {
        Parameter parameter;
        Map<String, Parameter> parameterMap = setParameterMap();

        Map<Integer, ParameterWithArg> parameters = reorderByOptions(parameterWithArgs, parameterMap);
        Map<String, Map<String, String>> allInformation = null;
        String path = null;
        for (int i = 0; i < parameters.size(); i += 1) {
            ParameterWithArg list = parameters.get(i);
            parameter = parameterMap.get(list.options());
            if ("--format-value".equals(list.options()) || "--format-field".equals(list.options())) {
                allInformation = parameter.doParameter(path, list.arg(), allInformation);
            } else {
                if (allInformation == null) {
                    path = list.arg();
                }
                allInformation = parameter.doParameter(list.options(), list.arg(), allInformation);

            }

        }

    }

    public Map<Integer, ParameterWithArg> reorderByOptions(
        List<ParameterWithArg> inputList,
        Map<String, Parameter> referenceMap

    ) {
        Map<Integer, ParameterWithArg> parameters = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<String, Parameter> entry : referenceMap.entrySet()) {
            String key = entry.getKey();
            for (ParameterWithArg parameterWithArg : inputList) {
                if (parameterWithArg.options().equals(key)) {
                    parameters.put(count, parameterWithArg);
                    count += 1;

                }
            }
        }

        return parameters;
    }

    public Map<String, Parameter> setParameterMap() {
        Map<String, Parameter> parameterMap = new LinkedHashMap<>();
        parameterMap.put("--from", new FromParameter());
        parameterMap.put("--to", new ToParameter());
        parameterMap.put("--filter-field", new FilterParameter());
        parameterMap.put("--filter-value", new FilterParameter());
        parameterMap.put("--path", new PathParameter());
        parameterMap.put("--format", new FormatParameter());
        return parameterMap;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * getClass().hashCode();
    }

}
