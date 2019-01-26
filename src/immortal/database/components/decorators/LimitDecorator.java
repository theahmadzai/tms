package immortal.database.components.decorators;

import immortal.database.components.Limit;

public final class LimitDecorator extends SqlDecorator {
    public LimitDecorator(Limit clause) {
        super(clause);
    }

    @Override
    public String toString() {
        if(clause == null) return new String();
        return "\nLIMIT " + clause;
    }
}
