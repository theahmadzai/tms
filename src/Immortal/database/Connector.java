package immortal.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface Connector {
    default Connection getConnection() throws SQLException {
        return Database.getInstance().getConnection();
    }
}
