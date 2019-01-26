package immortal.database;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import immortal.annotations.Column;
import immortal.annotations.Table;
import immortal.constants.Type;

public class Query {
	private static Connection connection;

	static {
		try {
			connection = Database.getInstance().getConnection();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static class Operations {
		private String key;
		private Object value;
		private boolean all;
		private PreparedStatement preparedStatement;

		private Operations() {
			all = true;
		}

		private Operations(String key, Object value) {
			this.key = key;
			this.value = value;
		}

		public Object select(Class<?> cls) {
			Table table = cls.getAnnotation(Table.class);

			StringBuffer sqlQuery = new StringBuffer("SELECT * FROM " + table.value());

			if(!all) sqlQuery.append(" WHERE " + key + " = ?");

			try {
			    preparedStatement = connection.prepareStatement(sqlQuery.toString());

			    if(!all) preparedStatement.setObject(1, value);

                ResultSet rs = preparedStatement.executeQuery();


                Object obj = cls.getDeclaredConstructor().newInstance();

                preparedStatement.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return 0;
		}

        public void update(Object obj) {

		}

		public int delete(Class<?> cls) {
			Table table = cls.getAnnotation(Table.class);

			StringBuffer sqlQuery = new StringBuffer("DELETE FROM " + table.value());

			if(!all) sqlQuery.append(" WHERE " + key + " = ?");

			try {
                preparedStatement = connection.prepareStatement(sqlQuery.toString());
                if(!all) preparedStatement.setObject(1, value);
                int rowsEffected = preparedStatement.executeUpdate();
                preparedStatement.close();
                return rowsEffected;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return 0;
		}
	}

	public Query() { }

	public static Operations all() {
		return new Operations();
	}

	public static Operations where(String key, Object value) {
		return new Operations(key, value);
	}

	public static void insert(Object obj) {
		Table table = obj.getClass().getAnnotation(Table.class);

		StringBuffer querySql = new StringBuffer("INSERT INTO " + table.value() + " (");
		StringBuffer valuesSql = new StringBuffer("VALUES (");

		try {
			Field[] fields = obj.getClass().getDeclaredFields();

			for(Field field : fields) {
				Column column = field.getAnnotation(Column.class);
				querySql.append(column.name() + ",");
				valuesSql.append("?,");
			}

			querySql.replace(
				querySql.length()-1,
				querySql.length(),
				") "
			);

			querySql.append(
				valuesSql.replace(
					valuesSql.length()-1,
					valuesSql.length(),
					")"
				)
			);

			System.out.println(querySql.toString());

			PreparedStatement preparedStatement = connection.prepareStatement(querySql.toString());

			for(int queryIndex=0; queryIndex<fields.length;) {
				Field field = fields[queryIndex++];
				field.setAccessible(true);
				Object value = field.get(obj);

				Column column = field.getAnnotation(Column.class);
				Type type = column.type();

				switch(type) {
					case INTEGER:
					case NUMERIC:
						preparedStatement.setInt(queryIndex, (int)value);
						break;

					case BOOLEAN:
						preparedStatement.setBoolean(queryIndex, (boolean)value);

					case TEXT:
						preparedStatement.setString(queryIndex, (String)value);

					default:
						preparedStatement.setString(queryIndex, value.toString());
						break;
				}
			}
			preparedStatement.execute();

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@Deprecated
	public static void insertOld(Object obj) {
		Class<?> c = obj.getClass();
		Field[] fields = c.getDeclaredFields();

		StringBuffer query = new StringBuffer("INSERT INTO " + c.getSimpleName().toLowerCase() + " (");
		StringBuffer values = new StringBuffer("VALUES (");
		List<Object> list = new LinkedList<>();

		try {
			for(Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(obj);
				list.add(value);
				query.append(field.getName().replaceAll("([A-Z]+)","\\_$1").toLowerCase() + ",");
				values.append("?,");
			}

			query.replace(query.length()-1, query.length(), ") ");
			values.replace(values.length()-1, values.length(), ")");
			query.append(values);

			System.out.println(query);

			PreparedStatement preparedStatement = connection.prepareStatement(query.toString());

			int queryIndex = 1;
			for(Object val : list) {
				if (int.class.equals(val.getClass())) {
					preparedStatement.setInt(queryIndex, (int)val);

				} else if (float.class.equals(val.getClass()) || double.class.equals(val.getClass())) {
					preparedStatement.setDouble(queryIndex,(double)val);

				} else if (boolean.class.equals(val.getClass())) {
					preparedStatement.setBoolean(queryIndex,(boolean)val);

				} else if (String.class.equals(val.getClass())) {
					preparedStatement.setString(queryIndex,(String)val);

				} else {
					preparedStatement.setString(queryIndex, val.toString());
				}
				queryIndex++;
			}

			preparedStatement.execute();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
