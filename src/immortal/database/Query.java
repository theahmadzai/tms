package immortal.database;

import immortal.Exceptions.DatabaseException;
import immortal.Exceptions.QueryException;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

class Query implements Connector {
    private PreparedStatement preparedStatement;
    private List<Object> results = new ArrayList<>();
    private int rowsEffected;
    private ResultSet resultSet;
    private int generatedKey;

    Query(String query) throws DatabaseException {
        try {
            preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            System.out.println(query);
        } catch(SQLException ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    int getRowsEffected() {
        return rowsEffected;
    }

    int getGeneratedKey() {
        return generatedKey;
    }

    List<Object> getResults() {
        return results;
    }

    Query setParameters(Object o, Map<String, Field> fields) throws QueryException {
        Collection<Field> values = fields.values();

        try {
            int queryIndex = 1;
            for (Field value : values) {
                preparedStatement.setObject(queryIndex++, value.get(o));
            }
        } catch(IllegalAccessException | SQLException ex) {
            throw new QueryException(ex.getMessage());
        }
        return this;
    }

    Query executeUpdate() throws QueryException {
        try {
            rowsEffected = preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) generatedKey = resultSet.getInt(1);

            resultSet.close();
            preparedStatement.close();
        } catch(SQLException ex) {
            throw new QueryException(ex.getMessage());
        }
        return this;
    }

    Query executeQuery(Class<?> c, Map<String, Field> fields) throws QueryException {
        try {
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Object o = c.getConstructor().newInstance();
                Iterator<Map.Entry<String, Field>> fieldsIterator = fields.entrySet().iterator();
                Field primaryKey = c.getSuperclass().getDeclaredField("id");
                primaryKey.setAccessible(true);
                primaryKey.set(o, resultSet.getObject("id"));

                while (fieldsIterator.hasNext()) {
                    Map.Entry<String, Field> pair = fieldsIterator.next();
                    pair.getValue().set(o, resultSet.getObject(pair.getKey()));
                }
                results.add(o);
            }

            preparedStatement.close();
            resultSet.close();
        } catch (Exception  ex) {
            throw new QueryException(ex.getMessage());
        }
        return this;
    }
}
