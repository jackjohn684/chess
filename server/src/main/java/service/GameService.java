package service;

import dataaccess.DataAccess;
import model.gameID;

public class GameService {
    private final DataAccess dataAccess;
    public GameService(DataAccess dataAccess) { this.dataAccess = dataAccess;}

    public gameID createGame(String auth, String gameName){
        return dataAccess.createGame(auth, gameName);
    }
    public void joinGame(int gameID, String playerColor){
        dataAccess.joinGame(gameID, playerColor);
    }
}
