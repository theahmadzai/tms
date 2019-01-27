package immortal.database;

import java.util.List;

public interface QueryBuilderInterface {
    public int insert(final Object o);

    public int update(final Object o);

    public int delete();

    public <T> List<T> select();

    public QueryBuilder where(final String key, final Object operator, final Object ...values);

    public QueryBuilder limit(int limit);
}
