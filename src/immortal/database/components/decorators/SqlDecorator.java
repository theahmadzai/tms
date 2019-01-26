package immortal.database.components.decorators;

import immortal.database.components.SqlInterface;

public abstract class SqlDecorator {
    protected SqlInterface clause;

    protected SqlDecorator(SqlInterface clause) {
        this.clause = clause;
    }

    @Override
    public abstract String toString();
}
