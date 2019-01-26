package immortal.database.components.decorators;

import immortal.database.components.Where;

final public class WhereDecorator extends SqlDecorator {
    public WhereDecorator(Where where) {
        super(where);
    }

    @Override
    public String toString() {
        if(clause == null) return new String();
        return "\nWHERE " + clause;
    }
}
