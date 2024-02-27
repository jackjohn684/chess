package service;

import dataaccess.DataAccess;
import exception.ResponseException;
import model.AuthToken;
import model.User;

import java.util.Collection;

public class UserService {
    private final DataAccess dataAccess;
    public UserService(DataAccess dataAccess) { this.dataAccess = dataAccess;}
    public User addUser(User user) throws ResponseException {
        return dataAccess.addUser(user);
    }
    public AuthToken addAuthToken(String username) throws ResponseException {
        return dataAccess.addAuthToken(username);
    }
    public Collection<User> listUsers() throws ResponseException {
        return dataAccess.listUsers();
    }

    public User getUser(String username) throws ResponseException {
        return dataAccess.getUser(username);
    }
    public void deleteUser(String username) throws ResponseException {
        dataAccess.deleteUser(username);
    }

    public void clearUsers() throws ResponseException {
        dataAccess.clearUsers();
    }
}
