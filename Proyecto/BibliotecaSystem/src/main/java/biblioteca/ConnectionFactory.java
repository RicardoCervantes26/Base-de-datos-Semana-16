package biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // Cambia el USER y PASS por los nuevos datos
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BibliotecaSistema;encrypt=true;trustServerCertificate=true;";

    private static final String USER = "AdminBiblio";
    private static final String PASS = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}