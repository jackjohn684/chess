package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import exception.ResponseException;
import model.Game;
import model.gameID;

import java.sql.SQLException;
import java.util.Collection;

public class GameService {
    private final DataAccess dataAccess;
    public GameService(DataAccess dataAccess) { this.dataAccess = dataAccess;}

    public gameID createGame(String auth, String gameName) throws SQLException, DataAccessException {
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
    public Collection<Game> listGames() throws ResponseException, SQLException, DataAccessException {
        return dataAccess.listGames();
    }
}
