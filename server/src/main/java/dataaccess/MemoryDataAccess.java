package dataaccess;

import model.AuthToken;
import model.User;

import java.util.Collection;
import java.util.HashMap;

public class MemoryDataAccess implements DataAccess {

    final private HashMap<String, User> users = new HashMap<>();
    final private HashMap<String, AuthToken> authTokens = new HashMap<>();
    public User addUser(User user) {
        user = new User(user.username(), user.password(), user.email());
        users.put(user.username(), user);
        return user;
    }
    public AuthToken addAuthToken(String username) {
        var auth = new AuthToken(username);
        authTokens.put(username, auth);
        return auth;
    }
    public Collection<User> listUsers(){
        return users.values();
    }


    public void deleteUser(String username) {users.remove(username);}

    public User getUser(String username) {return users.get(username);}

    public void clearUsers() {users.clear();}

    public void clear() {users.clear(); authTokens.clear();}
}
