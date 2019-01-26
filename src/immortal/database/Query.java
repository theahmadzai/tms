package immortal.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Query implements Connector {
    private PreparedStatement preparedStatement;
    private List<Object> results = new ArrayList<>();
    private int rowsEffected;

    public Query(String query) throws SQLException {
        System.out.println(query);
        preparedStatement = getConnection().prepareStatement(query);
    }

    public int getRowsEffected() {
        return rowsEffected;
    }

    public List<Object> getResults() {
        return results;
    }

    public Query setParameters(Object o, Map<String, Field> fields) throws IllegalArgumentException, IllegalAccessException, SQLException {
        Collection<Field> values = fields.values();

        int queryIndex = 1;
        for(Field value : values) {
            preparedStatement.setObject(queryIndex++, value.get(o));
        }

        return this;
    }

    public Query executeUpdate() throws SQLException {
        rowsEffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        return this;
    }

    public Query executeQuery(Class<?> c, Map<String, Field> fields) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            Object o = c.getConstructor().newInstance();
            Iterator<Map.Entry<String, Field>> fieldsIterator = fields.entrySet().iterator();

            while(fieldsIterator.hasNext()) {
                Map.Entry<String, Field> pair = fieldsIterator.next();
                pair.getValue().set(o, resultSet.getObject(pair.getKey()));
            }

            results.add(o);
        }

        preparedStatement.close();
        return this;
    }
}
