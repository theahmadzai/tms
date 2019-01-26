package immortal.database.components.decorators;

import immortal.database.components.Set;

public final class SetDecorator extends SqlDecorator {
    public SetDecorator(Set set) {
        super(set);
    }

    @Override
    public String toString() {
        return "\nSET " + clause;
    }
}
