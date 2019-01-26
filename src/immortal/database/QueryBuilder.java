package immortal.database;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import immortal.annotations.Column;
import immortal.annotations.Table;
import immortal.database.components.BracketDecorator;
import immortal.database.components.Columns;
import immortal.database.components.Set;
import immortal.database.components.Values;
import immortal.database.components.ValuesDecorator;
import immortal.database.components.Where;
import immortal.database.components.WhereDecorator;

public class QueryBuilder implements QueryBuilderInterface {
    private Class<?> c;
    private String table;
    private Map<String, Field> fields = new HashMap<>();
    private Map<String, String> columnValue = new HashMap<>();//TODO REMOVE THIS AND USE ITERABLE INTERFACE ON SET
    private Set set; // STORE COLUMN VALUE HERE AND PROVIDE ADD REMOVE METHOD
    private Where where;
    private Columns columns;
    private Values values;

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

    private ResultSet execute(String sqlQuery) {
        ResultSet result = null;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            System.out.println(preparedStatement.isClosed());
            result = preparedStatement.executeQuery();
            result.next();
            System.out.println(result.getObject("amount"));
//            preparedStatement.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public int insert(final Object o) {
        StringBuffer sqlQuery = new StringBuffer("INSERT INTO " + table + columns);
        return 0;
    }

    @Override
    public int update(final Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete() {
        System.out.println("DELETE FROM " + table +
                new WhereDecorator(where)
        );
        System.out.println("INSERT INTO " + table +
                new BracketDecorator(columns) +
                new ValuesDecorator(values)
        );
        System.out.println("UPDATE " + table +

                new WhereDecorator(where)
        );

        System.out.println("INSERT INTO " + table +
                new BracketDecorator(columns) +
                new ValuesDecorator(values) +
                new WhereDecorator(where)
        );

//        return (int) execute("DELETE FROM " + table + where);
        return 3;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T select() {
        String sqlQuery = "SELECT * FROM " + table;
        System.out.println(sqlQuery);
        T a = (T) execute(sqlQuery);
        try {
            System.out.println(((ResultSet) a).getString("amount"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public QueryBuilder where(String key, String operator, Object value) {
        where = new Where(key, operator, value);
        return this;
    }

    @Override
    public QueryBuilder all() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public QueryBuilder first() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public QueryBuilder last() {
        // TODO Auto-generated method stub
        return null;
    }
}
