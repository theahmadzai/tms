package immortal.database;

import immortal.annotations.Column;
import immortal.annotations.Table;
import immortal.database.components.*;
import immortal.database.components.decorators.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author theahmadzai
 * @description SQL Queries Pattern
 * INSERT INTO {TABLE} {COLUMNS} {VALUES}
 * UPDATE {TABLE} {SET} :{WHERE}
 * DELETE FROM {TABLE} :{WHERE}
 * SELECT *{COLUMNS} FROM {TABLE} :{WHERE} :{LIMIT} :{GROUP} :{ORDER}
 */
//TODO REMOVE THIS AND USE ITERABLE INTERFACE ON SET
// STORE COLUMN VALUE HERE AND PROVIDE ADD REMOVE METHOD
public class QueryBuilder implements QueryBuilderInterface {
    private Class<?> c;
    private Query query;
    private String table;
    private Map<String, Field> fields = new HashMap<>();
    private Map<String, String> columnValue = new HashMap<>();
    private Columns columns = new Columns(columnValue.keySet());
    private Values values = new Values(columnValue.values());
    private Set set = new Set(columnValue);
    private Where where;
    private Limit limit;

    public QueryBuilder(Class<?> c) {
        this.c = c;
        table = c.getAnnotation(Table.class).value();
        Field[] fields = c.getDeclaredFields();

        for(Field field : fields) {
            if(!field.isAnnotationPresent(Column.class)) continue;
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            this.fields.put(column.value(), field);
            columnValue.put(column.value(), "?");
        }
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

        return query.getGeneratedKey();
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

        } catch (Exception ex) {
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
    public QueryBuilder where(String key, Object operator, Object ...values) {
        where = new Where(key, operator, values);
        return this;
    }

    @Override
    public QueryBuilder limit(int length) {
        limit = new Limit(length);
        return this;
    }
}
