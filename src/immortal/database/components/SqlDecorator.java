package immortal.database.components;

public abstract class SqlDecorator {
    protected SqlInterface clause;

    protected SqlDecorator(SqlInterface clause) {
        this.clause = clause;
    }

    @Override
    public abstract String toString();
}
