package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;

public class DB_Connection {

    private static final String SERVER_NAME = "localhost"; //vestergade.no-ip.org
    private static final String DATABASE_INSTANCE = "SQLEXPRESS";
    private static final int PORTNO = 1433; //64877;
    private static final String DATABASE_NAME = "TOR DB";
    private static final String USERNAME = "Tor";
    private static final String PASSWORD = "qwerty";

    private Connection m_connection;

    private static DB_Connection m_instance;

    /**
     * Establishes a connection to the database
     */
    private DB_Connection() {
        SQLServerDataSource ds = new SQLServerDataSource();

        ds.setServerName(SERVER_NAME);
        ds.setDatabaseName(DATABASE_NAME);
        ds.setInstanceName(DATABASE_INSTANCE);
        ds.setPortNumber(PORTNO);
        ds.setUser(USERNAME);
        ds.setPassword(PASSWORD);
        try {
            m_connection = ds.getConnection();
        } catch (SQLServerException ex) {
        }
    }

    /**
     *
     * @return the connection
     */
    public Connection getConnection() {
        return m_connection;
    }

    /**
     *
     * @return m_instance
     */
    public static DB_Connection getInstance() {
        if (m_instance == null) {
            m_instance = new DB_Connection();
        }

        return m_instance;
    }
}
