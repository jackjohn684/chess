package service;

import dataaccess.DataAccess;
import exception.ResponseException;

public class ClearService {
    private final DataAccess dataAccess;
    public ClearService(DataAccess dataAccess) { this.dataAccess = dataAccess;}
    public void clear(UserService userService, GameService gameService) throws ResponseException {
        userService.clearUsers();
        gameService.clearGames();
    }
}
