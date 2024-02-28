package dataaccess;

import model.AuthToken;
import model.Game;
import model.User;
import model.gameID;

import java.util.Collection;

public interface DataAccess {
    User addUser(User user);
    public Collection<User> listUsers();
    public User getUser(String userName);
    public void deleteUser(String userName);
    public void deleteAuthToken(String auth);
    public void clearUsers();
    public AuthToken addAuthToken(String username);
    public gameID createGame(String auth, String gameName);
    public AuthToken getAuthToken(String auth);
    public String joinGame(int gameID, String playerColor, String username);
    public Game getGame(int gameID);
    public void clearGames();
    public Collection<Game> listGames();


    public void clear();
}
