package hexlet.code.repository;

import java.sql.Connection;

public abstract class BaseRepository {
    protected static Connection conn;

    public static void setConn(Connection connection) {
        conn = connection;
    }
}
