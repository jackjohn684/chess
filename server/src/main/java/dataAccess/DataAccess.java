package dataAccess;

import model.AuthToken;
import model.Game;
import model.User;
import model.gameID;

import java.sql.SQLException;
import java.util.Collection;

public interface DataAccess {
    User addUser(User user) throws SQLException, DataAccessException;
    public Collection<User> listUsers();
    public User getUser(String userName) throws DataAccessException, SQLException;
    public void deleteUser(String userName) throws DataAccessException, SQLException;
    public void deleteAuthToken(String auth);
    public void clearUsers() throws DataAccessException, SQLException;
    public AuthToken addAuthToken(String username) throws SQLException, DataAccessException;
    public gameID createGame(String auth, String gameName);
    public AuthToken getAuthToken(String auth);
    public String joinGame(int gameID, String playerColor, String username);
    public Game getGame(int gameID);
    public void clearGames();
    public Collection<Game> listGames();


    public void clear();
}
