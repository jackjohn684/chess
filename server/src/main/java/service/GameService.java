package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import exception.ResponseException;
import model.Game;

import java.sql.SQLException;

public class GameService {
    private final DataAccess dataAccess;
    public GameService(DataAccess dataAccess) { this.dataAccess = dataAccess;}

    public int createGame(String auth, String gameName) throws SQLException, DataAccessException {
        return dataAccess.createGame(auth, gameName);
    }
    public void clearGames() throws SQLException, DataAccessException {
        dataAccess.clearGames();
    }
    public String joinGame(int gameID, String playerColor, String username) throws SQLException, DataAccessException {
        return dataAccess.joinGame(gameID, playerColor, username);
    }
    public Game getGame(int gameID) throws SQLException, DataAccessException {
        return dataAccess.getGame(gameID);
    }
    public Game[] listGames() throws ResponseException, SQLException, DataAccessException {
        return dataAccess.listGames();
    }
}
