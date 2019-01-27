package immortal.database.components;

import java.util.Arrays;

import immortal.constants.Operator;

public final class Where implements SqlInterface {
    private final String key;
    private final Object operator;
    private final Object[] values;

    public Where(final String key, final Object operator, final Object ...values) {
        this.key = key;
        this.operator = operator;
        this.values = values;
    }

    @Override
    public String toString() {
        if(operator.toString().equalsIgnoreCase("in")) {
            return key + " " +
                    Operator.IN + " (" +
                    Arrays.toString(values).replaceAll("\\[|\\]", "") + ")";
        }

        if(operator.toString().equalsIgnoreCase("like")) {
            return key + " " +
                    Operator.LIKE + " '" +
                    Arrays.toString(values).replaceAll("\\[|\\]", "") + "'";
        }

        return key + " " +
                    operator.toString() + " " +
                    Arrays.toString(values).replaceAll("\\[|\\]", "");
    }
}
