package immortal.database.components;

public final class Where implements SqlInterface {
    private final String key;
    private final String operator;
    private final Object value;

    public Where(final String key, final String operator, final Object value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.key + " " + this.operator + " " + this.value.toString();
    }
}
