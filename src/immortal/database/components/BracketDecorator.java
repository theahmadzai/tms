package immortal.database.components;

public final class BracketDecorator extends SqlDecorator {
    public BracketDecorator(SqlInterface clause) {
        super(clause);
    }

    @Override
    public String toString() {
        return "\n(" + clause + ")";
    }
}
