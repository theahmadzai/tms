package immortal.database.components;

final public class WhereDecorator extends SqlDecorator {
    public WhereDecorator(Where where) {
        super(where);
    }

    @Override
    public String toString() {
        return "\nWHERE " + clause;
    }
}
