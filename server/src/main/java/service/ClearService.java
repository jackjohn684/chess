package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import exception.ResponseException;

import java.sql.SQLException;

public class ClearService {
    private final DataAccess dataAccess;
    public ClearService(DataAccess dataAccess) { this.dataAccess = dataAccess;}
    public void clear(UserService userService, GameService gameService) throws ResponseException, SQLException, DataAccessException {
        userService.clearUsers();
        gameService.clearGames();
    }
}
