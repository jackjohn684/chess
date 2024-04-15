package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DatabaseManager {
    private static final String databaseName;
    private static final String user;
    private static final String password;
    private static final String connectionUrl;
    private static Connection conn;

    /*
     * Load the database information for the db.properties file.
     */
    static {
        try {
            try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
                if (propStream == null) throw new Exception("Unable to load db.properties");
                Properties props = new Properties();
                props.load(propStream);
                databaseName = props.getProperty("db.name");
                user = props.getProperty("db.user");
                password = props.getProperty("db.password");

                var host = props.getProperty("db.host");
                var port = Integer.parseInt(props.getProperty("db.port"));
                connectionUrl = String.format("jdbc:mysql://%s:%d", host, port);
            }
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties. " + ex.getMessage());
        }
    }

    /**
     * Creates the database if it does not already exist.
     */
    static void createDatabase() throws DataAccessException {
        try {
            var statement = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            conn = DriverManager.getConnection(connectionUrl, user, password);
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
            conn.setCatalog(databaseName);
            addTables();
            /*
            try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES(?, ?, ?)")) {
                preparedStatement.setString(1, "jack123");
                preparedStatement.setString(2, "yeeee");
                preparedStatement.setString(3, "yeeeeee");

                preparedStatement.executeUpdate();
            }
            try (var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM user")) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        var id = rs.getString("username");
                        var name = rs.getString("password");
                        var type = rs.getString("email");

                        System.out.printf("id: %s, name: %s, type: %s%n", id, name, type);
                    }
                }
            }
             */
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    static Connection connect() throws SQLException {
        return DriverManager.getConnection(connectionUrl, user, password);
    }
    static void addTables() throws DataAccessException {
        try {
            var createUserTable = createUserTable();
            var createAuthTable = createAuthTable();
            var createGameTable = createGameTable();
            try (var createTableStatement = conn.prepareStatement(createUserTable)) {
                createTableStatement.executeUpdate();
            }
            try (var createTableStatement = conn.prepareStatement(createAuthTable)) {
                createTableStatement.executeUpdate();
            }
            try (var createTableStatement = conn.prepareStatement(createGameTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
   static String createUserTable(){
        String createUserTable;
        createUserTable = """            
                CREATE TABLE  IF NOT EXISTS user (
                username VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                PRIMARY KEY (username)
            )
                """;
        return createUserTable;
    }

    static String createAuthTable() {
        String createAuthTable;
        createAuthTable = """
               CREATE TABLE  IF NOT EXISTS auth (
                    authToken VARCHAR(255) NOT NULL,
                    username VARCHAR(255) NOT NULL,
                    PRIMARY KEY (authToken)
                )""";
        return createAuthTable;
    }
    static String createGameTable() {
        String createGameTable;
        createGameTable = """
                 CREATE TABLE  IF NOT EXISTS game (
                                gameID INT NOT NULL AUTO_INCREMENT,
                                whiteUsername VARCHAR(255),
                                blackUsername VARCHAR(255),
                                gameName VARCHAR(255),
                                PRIMARY KEY (gameID)
                            )
                """;
        return createGameTable;
    }

    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DbInfo.getConnection(databaseName)) {
     * // execute SQL statements.
     * }
     * </code>
     */
    static Connection getConnection() throws DataAccessException {
        try {
            conn = DriverManager.getConnection(connectionUrl, user, password);
            createDatabase();
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
