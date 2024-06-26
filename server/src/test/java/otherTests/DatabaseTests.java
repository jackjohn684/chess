package otherTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
import server.Server;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTests {
    private static TestServerFacade serverFacade;
    private static Server server;
    @BeforeAll
    public static void init() {
        startServer();
        serverFacade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    public static void startServer() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        serverFacade = new TestServerFacade("localhost", Integer.toString(port));
    }

    @Test
    public void register() throws TestException {
        var initialRowCount = getDatabaseRows();
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = "ExistingUser";
        registerRequest.password = "existingUserPassword";
        registerRequest.email = "eu@mail.com";
        TestModels.TestLoginRegisterResult regResult = serverFacade.register(registerRequest);
        var auth = regResult.authToken;
    }






        private int getDatabaseRows() {
            int rows = 0;
            try {
                Class<?> clazz = Class.forName("dataAccess.DatabaseManager");
                Method getConnectionMethod = clazz.getDeclaredMethod("getConnection");
                getConnectionMethod.setAccessible(true);

                Object obj = clazz.getDeclaredConstructor().newInstance();
                try (Connection conn = (Connection) getConnectionMethod.invoke(obj);) {
                    try (var statement = conn.createStatement()) {
                        for (String table : getTables(conn)) {
                            var sql = "SELECT count(*) FROM " + table;
                            try (var resultSet = statement.executeQuery(sql)) {
                                if (resultSet.next()) {
                                    rows += resultSet.getInt(1);
                                }
                            }
                        }
                    }

                }
            } catch (Exception ex) {
                Assertions.fail("Unable to load database in order to verify persistence. Are you using dataAccess.DatabaseManager to set your credentials?");
            }

            return rows;
        }

        private List<String> getTables(Connection conn) throws SQLException {
            String sql = """
                    SELECT table_name
                    FROM information_schema.tables
                    WHERE table_schema = DATABASE();
                """;

            List<String> tableNames = new ArrayList<>();
            try (var preparedStatement = conn.prepareStatement(sql)) {
                try (var resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        tableNames.add(resultSet.getString(1));
                    }
                }
            }
            return tableNames;
        }
}
