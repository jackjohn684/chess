package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import exception.ResponseException;
import model.Game;
import model.GameInfo;

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
    public String joinGame(GameInfo gameInfo, String username) throws SQLException, DataAccessException {
        return dataAccess.joinGame(gameInfo, username);
    }
    public Game getGame(String gameID) throws SQLException, DataAccessException {
        return dataAccess.getGame(gameID);
    }
    public Game[] listGames() throws ResponseException, SQLException, DataAccessException {
        return dataAccess.listGames();
    }
}
