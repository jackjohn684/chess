package dataAccess;

import model.*;

import java.sql.SQLException;
import java.util.Collection;

public interface DataAccess {
    User addUser(User user) throws SQLException, DataAccessException;
    public Collection<User> listUsers() throws DataAccessException, SQLException;
    public User getUser(String userName) throws DataAccessException, SQLException;
    public void deleteUser(String userName) throws DataAccessException, SQLException;
    public void deleteAuthToken(String auth) throws DataAccessException, SQLException;
    public void clearUsers() throws DataAccessException, SQLException;
    public void deleteGame(String gameID) throws DataAccessException, SQLException;
    public AuthToken addAuthToken(String username) throws SQLException, DataAccessException;
    public gameID createGame(String auth, String gameName) throws DataAccessException, SQLException;
    public String makeGame(Game game) throws DataAccessException, SQLException;
    public AuthToken getAuthToken(String auth) throws DataAccessException, SQLException;
    public String joinGame(GameInfo gameInfo, String username) throws SQLException, DataAccessException;
    public Game getGame(String gameID) throws DataAccessException, SQLException;
    public void clearGames() throws DataAccessException, SQLException;
    public Collection<Game> listGames() throws DataAccessException, SQLException;


    public void clear() throws SQLException, DataAccessException;
}
