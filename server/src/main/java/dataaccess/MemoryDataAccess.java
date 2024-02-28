package dataaccess;

import chess.ChessGame;
import model.AuthToken;
import model.Game;
import model.User;
import model.gameID;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class MemoryDataAccess implements DataAccess {

    final private HashMap<String, User> users = new HashMap<>();
    final private HashMap<String, AuthToken> authTokens = new HashMap<>();
    final private HashMap<Integer, Game> games = new HashMap<>();
    public User addUser(User user) {
        user = new User(user.username(), user.password(), user.email());
        users.put(user.username(), user);
        return user;
    }
    public AuthToken addAuthToken(String username) {
        var auth = new AuthToken(username);
        authTokens.put(auth.getAuth(), auth);
        return auth;
    }
    public void deleteAuthToken(String auth) {
        authTokens.remove(auth);
    }
    public gameID createGame(String auth, String gameName){
        Random rnd = new Random();
        int randomNumber = 1000 + rnd.nextInt(9000);
        var myGameId = new gameID(randomNumber);
        games.put(myGameId.gameID(), new Game(myGameId.gameID(), "", "", gameName, new ChessGame()) );
        return myGameId;
    }

    public Collection<User> listUsers(){
        return users.values();
    }


    public void deleteUser(String username) {users.remove(username);}

    public User getUser(String username) {return users.get(username);}
    public AuthToken getAuthToken(String auth) { return authTokens.get(auth);}
    public void clearUsers() {users.clear();}

    public void clear() {users.clear(); authTokens.clear();}

    public void joinGame(int gameID, String playerColor){
        if playerColor
    }
}
