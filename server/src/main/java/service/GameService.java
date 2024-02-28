package service;

import dataaccess.DataAccess;
import exception.ResponseException;
import model.Game;
import model.gameID;

import java.util.Collection;

public class GameService {
    private final DataAccess dataAccess;
    public GameService(DataAccess dataAccess) { this.dataAccess = dataAccess;}

    public gameID createGame(String auth, String gameName){
        return dataAccess.createGame(auth, gameName);
    }
    public void clearGames(){
        dataAccess.clearGames();
    }
    public String joinGame(int gameID, String playerColor, String username){
        return dataAccess.joinGame(gameID, playerColor, username);
    }
    public Game getGame(int gameID){
        return dataAccess.getGame(gameID);
    }
    public Collection<Game> listGames() throws ResponseException {
        return dataAccess.listGames();
    }
}
