package dataAccess;

import model.AuthToken;
import model.Game;
import model.User;

import java.sql.SQLException;
import java.util.Collection;

public interface DataAccess {
    User addUser(User user) throws SQLException, DataAccessException;
    public Collection<User> listUsers() throws DataAccessException, SQLException;
    public User getUser(String userName) throws DataAccessException, SQLException;
    public void deleteUser(String userName) throws DataAccessException, SQLException;
    public void deleteAuthToken(String auth) throws DataAccessException, SQLException;
    public void clearUsers() throws DataAccessException, SQLException;
    public void deleteGame(int gameID) throws DataAccessException, SQLException;
    public AuthToken addAuthToken(String username) throws SQLException, DataAccessException;
    public int createGame(String auth, String gameName) throws DataAccessException, SQLException;
    public String makeGame(Game game) throws DataAccessException, SQLException;
    public AuthToken getAuthToken(String auth) throws DataAccessException, SQLException;
    public String joinGame(int gameID, String playerColor, String username) throws SQLException, DataAccessException;
    public Game getGame(int gameID) throws DataAccessException, SQLException;
    public void clearGames() throws DataAccessException, SQLException;
    public Game[] listGames() throws DataAccessException, SQLException;


    public void clear() throws SQLException, DataAccessException;
}
