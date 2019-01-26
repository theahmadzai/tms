package immortal.database.components;

public final class ValuesDecorator extends SqlDecorator {
    public ValuesDecorator(Values clause) {
        super(clause);
    }

    @Override
    public String toString() {
        return "\nVALUES " + new BracketDecorator(clause);
    }
}
