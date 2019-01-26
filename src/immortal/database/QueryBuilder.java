package immortal.database;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import immortal.annotations.Column;
import immortal.annotations.Table;
import immortal.database.components.Columns;
import immortal.database.components.Limit;
import immortal.database.components.Set;
import immortal.database.components.Values;
import immortal.database.components.Where;
import immortal.database.components.decorators.BracketDecorator;
import immortal.database.components.decorators.LimitDecorator;
import immortal.database.components.decorators.SetDecorator;
import immortal.database.components.decorators.ValuesDecorator;
import immortal.database.components.decorators.WhereDecorator;

public class QueryBuilder implements QueryBuilderInterface {
    private Class<?> c;
    private String table;
    private Map<String, Field> fields = new HashMap<>();
    private Map<String, String> columnValue = new HashMap<>();//TODO REMOVE THIS AND USE ITERABLE INTERFACE ON SET
    private Set set; // STORE COLUMN VALUE HERE AND PROVIDE ADD REMOVE METHOD
    private Where where;
    private Limit limit;
    private Columns columns;
    private Values values;
    private Query query;

    public QueryBuilder(Class<?> c) {
        this.c = c;
        table = c.getAnnotation(Table.class).value();
        Field[] fields = c.getDeclaredFields();


        for(Field field : fields) {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            this.fields.put(column.value(), field);
            columnValue.put(column.value(), "?");
        }

        set = new Set(columnValue);
        columns = new Columns(columnValue.keySet());
        values = new Values(columnValue.values());
    }

    @Override
    public int insert(final Object o) {
        try {
            query = new Query("INSERT INTO " + table + " " +
                new BracketDecorator(columns) +
                new ValuesDecorator(values)
            )
            .setParameters(o, fields)
            .executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return query.getRowsEffected();
    }

    @Override
    public int update(final Object o) {
        try {
            query = new Query("UPDATE " + table +
                new SetDecorator(set) +
                new WhereDecorator(where)
            )
            .setParameters(o, fields)
            .executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return query.getRowsEffected();
    }

    @Override
    public int delete() {
        try {
            query = new Query("DELETE FROM " + table +
                new WhereDecorator(where)
            )
            .executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return query.getRowsEffected();
    }

    @Override
    public <T> List<T> select() {
        try {
            query = new Query("SELECT * FROM " + table +
                new WhereDecorator(where) +
                new LimitDecorator(limit)
            )
            .executeQuery(c, fields);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (List<T>) query.getResults();
    }

    @Override
    public QueryBuilder where(String key, String operator, Object value) {
        where = new Where(key, operator, value);
        return this;
    }

    @Override
    public QueryBuilder limit(int length) {
        limit = new Limit(length);
        return this;
    }
}
