package passoffTests.serverTests;

import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestModels;
import server.Server;
import server.ServerFacade;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {
    private static TestModels.TestUser existingUser;

    private static TestModels.TestUser newUser;

    private static TestModels.TestCreateRequest createRequest;

    private static Server server;

    private static ServerFacade serverFacade;
    private String existingAuth;
    @BeforeAll
    public static void init() {
        server = new Server();
        server.run(0);
        var url = "http://localhost:" + server.port();
        serverFacade = new ServerFacade(url);
    }
    @BeforeEach
    void clear() {assertDoesNotThrow(() ->serverFacade.clear());}
    @AfterAll
    static void stopServer() {
        Server.stop();
    }

    @Test
    void addUser(){
        var yee = new User("jack684", "QWerTy55", "jackjohn684@gmail.com");
        var result = assertDoesNotThrow(()-> serverFacade.register(yee));
    }
    public static void assertUserEqual(User expected, User actual) {
        assertEquals(expected.username(), actual.username());
        assertEquals(expected.password(), actual.password());
        assertEquals(expected.email(), actual.email());
    }
}