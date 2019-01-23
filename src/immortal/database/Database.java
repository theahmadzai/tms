package immortal.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("WeakerAccess")
public class Database {
	private static Database instance = null;
	private Connection connection;

	private Database() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:toll.db");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public static Database getInstance() throws SQLException {
		if(instance == null || instance.getConnection().isClosed()) {
			instance = new Database();
		}

		return instance;
	}
}
