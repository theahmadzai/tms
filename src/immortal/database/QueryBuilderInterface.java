package immortal.database;

public interface QueryBuilderInterface extends Connector {
    public int insert(final Object o);

    public int update(final Object o);

    public int delete();

//    public Object select();

    public QueryBuilder where(final String key, final String operator, final Object value);

    public QueryBuilder all();

    public QueryBuilder first();

    public QueryBuilder last();

    public <T> T select();
}
