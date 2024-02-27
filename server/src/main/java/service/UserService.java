package service;

import dataaccess.DataAccess;
import exception.ResponseException;
import model.User;

import java.util.Collection;

public class UserService {
    private final DataAccess dataAccess;
    public UserService(DataAccess dataAccess) { this.dataAccess = dataAccess;}
    public User addUser(User user) throws ResponseException {
        return dataAccess.addUser(user);
    }

    public Collection<User> listUsers() throws ResponseException {
        return dataAccess.listUsers();
    }

    public User getUser(String userName) throws ResponseException {
        return dataAccess.getUser(userName);
    }
    public void deleteUser(String userName) throws ResponseException {
        dataAccess.deleteUser(userName);
    }
    public void clearUsers() throws ResponseException {
        dataAccess.clearUsers();
    }
}
