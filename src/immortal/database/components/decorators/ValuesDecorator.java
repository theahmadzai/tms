package immortal.database.components.decorators;

import immortal.database.components.Values;

public final class ValuesDecorator extends SqlDecorator {
    public ValuesDecorator(Values clause) {
        super(clause);
    }

    @Override
    public String toString() {
        return "\nVALUES " + new BracketDecorator(clause);
    }
}
