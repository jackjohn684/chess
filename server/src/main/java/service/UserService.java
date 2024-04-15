package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import exception.ResponseException;
import model.AuthToken;
import model.User;

import java.sql.SQLException;
import java.util.Collection;

public class UserService {
    private final DataAccess dataAccess;
    public UserService(DataAccess dataAccess) { this.dataAccess = dataAccess;}
    public User addUser(User user) throws ResponseException, SQLException, DataAccessException {
        return dataAccess.addUser(user);
    }
    public AuthToken addAuthToken(String username) throws ResponseException, SQLException, DataAccessException {
        return dataAccess.addAuthToken(username);
    }
    public Collection<User> listUsers() throws ResponseException, SQLException, DataAccessException {
        return dataAccess.listUsers();
    }
    public User getUser(String username) throws ResponseException, SQLException, DataAccessException {
        return dataAccess.getUser(username);
    }
    public void deleteUser(String username) throws ResponseException, SQLException, DataAccessException {
        dataAccess.deleteUser(username);
    }
    public void deleteAuthToken(String auth) throws ResponseException, SQLException, DataAccessException {
        dataAccess.deleteAuthToken(auth);
    }
    public AuthToken getAuthToken(String auth) throws ResponseException, SQLException, DataAccessException {
       return dataAccess.getAuthToken(auth);
    }

    public void clearUsers() throws ResponseException, SQLException, DataAccessException {
        dataAccess.clearUsers();

    }
}
