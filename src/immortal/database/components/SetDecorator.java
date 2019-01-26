package immortal.database.components;

public final class SetDecorator extends SqlDecorator {
    public SetDecorator(Set set) {
        super(set);
    }

    @Override
    public String toString() {
        return "\nSET" + clause;
    }
}
