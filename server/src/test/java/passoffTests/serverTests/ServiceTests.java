package passoffTests.serverTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import exception.ResponseException;
import org.junit.jupiter.api.BeforeEach;
import service.ClearService;
import service.GameService;
import service.UserService;

import java.sql.SQLException;

public class ServiceTests {

    static final UserService userService = new UserService((new MemoryDataAccess()));
    static final GameService gameService = new GameService(new MemoryDataAccess());
    static final ClearService clearService = new ClearService((new MemoryDataAccess()));
    @BeforeEach
    void clear() throws ResponseException, SQLException, DataAccessException {
        clearService.clear(userService, gameService);
    }

    void listUsers() throws ResponseException {
    }
    void getUser(String username) throws ResponseException {
    }
     void deleteUser(String username) throws ResponseException {
    }
     void deleteAuthToken(String auth) throws ResponseException {
    }
    void getAuthToken(String auth) throws ResponseException {
    }

    void clearUsers() throws ResponseException {

    }
}
