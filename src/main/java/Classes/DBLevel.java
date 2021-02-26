package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBLevel {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DATABASE_URL = "jdbc:postgresql://dbtest.ct61ihyxgavq.eu-central-1.rds.amazonaws.com:5432/TestDB";
    static final String DATABASE_USER = "Postgresql";
    static final String DATABASE_PASSWORD = "postgresqlpa";
    private Connection connection;

    public DBLevel() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        this.connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

}
