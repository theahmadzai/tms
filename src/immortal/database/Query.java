package immortal.database;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Query implements Connector {
    private PreparedStatement preparedStatement;
    private List<Object> results = new ArrayList<>();
    private int rowsEffected;
    private ResultSet resultSet;
    private int generatedKey;

    public Query(String query) throws SQLException {
        System.out.println(query);
        preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }

    public int getRowsEffected() {
        return rowsEffected;
    }

    public int getGeneratedKey() {
        return generatedKey;
    }

    public List<Object> getResults() {
        return results;
    }

    public Query setParameters(Object o, Map<String, Field> fields) throws Exception {
        Collection<Field> values = fields.values();

        int queryIndex = 1;
        for(Field value : values) {
            preparedStatement.setObject(queryIndex++, value.get(o));
        }

        return this;
    }

    public Query executeUpdate() throws Exception {
        rowsEffected = preparedStatement.executeUpdate();
        resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) {
            generatedKey = resultSet.getInt(1);
        }

        resultSet.close();
        preparedStatement.close();
        return this;
    }

    public Query executeQuery(Class<?> c, Map<String, Field> fields) throws Exception {
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            Object o = c.getConstructor().newInstance();
            Iterator<Map.Entry<String, Field>> fieldsIterator = fields.entrySet().iterator();
            Field primaryKey = c.getSuperclass().getDeclaredField("id");
            primaryKey.setAccessible(true);
            primaryKey.set(o, resultSet.getObject("id"));

            while(fieldsIterator.hasNext()) {
                Map.Entry<String, Field> pair = fieldsIterator.next();
                pair.getValue().set(o, resultSet.getObject(pair.getKey()));
            }

            results.add(o);
        }

        preparedStatement.close();
        resultSet.close();
        return this;
    }
}
