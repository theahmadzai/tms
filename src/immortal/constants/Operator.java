package immortal.constants;

public enum Operator {
    EQ("="), IN("IN"), LE("<"), GE(">"), LEQ("<="), GEQ(">="), LIKE("LIKE");

    private final String operator;

    private Operator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return this.operator;
    }
}
