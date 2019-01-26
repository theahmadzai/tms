package immortal.database.components.decorators;

import immortal.database.components.SqlInterface;

public final class BracketDecorator extends SqlDecorator {
    public BracketDecorator(SqlInterface clause) {
        super(clause);
    }

    @Override
    public String toString() {
        return "\n(" + clause + ")";
    }
}
