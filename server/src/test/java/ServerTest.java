import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
        var port = server.run(0);
        var url = "http://localhost:" + port;
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade(url);
/*
        existingUser = new TestModels.TestUser();
        existingUser.username = "ExistingUser";
        existingUser.password = "existingUserPassword";
        existingUser.email = "eu@mail.com";

        newUser = new TestModels.TestUser();
        newUser.username = "NewUser";
        newUser.password = "newUserPassword";
        newUser.email = "nu@mail.com";

        createRequest = new TestModels.TestCreateRequest();
        createRequest.gameName = "testGame";
 */
    }
    @AfterAll
    static void stopServer() {
        Server.stop();
    }

    @Test
    void addUser(){
        var yee = new User("jack684", "QWerTy55", "jackjohn684@gmail.com");
        var result = assertDoesNotThrow(()-> serverFacade.addUser(yee));
        assertUserEqual(yee, result);
    }
    void getUser() {
        var yee = "jack684";
        var result = assertDoesNotThrow(( -> serverFacade.getUser(yee));
    }
    public static void assertUserEqual(User expected, User actual) {
        assertEquals(expected.userName(), actual.userName());
        assertEquals(expected.password(), actual.password());
        assertEquals(expected.email(), actual.email());
    }
}




