package dataaccess;

import model.User;

import java.util.Collection;
import java.util.HashMap;
public class MemoryDataAccess implements DataAccess {

    final private HashMap<String, User> users = new HashMap<>();
    final private HashMap<String, String> authTokens = new HashMap<>();
    public User addUser(User user) {
        user = new User(user.userName(), user.password(), user.email());
        users.put(user.userName(), user);
        return user;
    }
    public Collection<User> listUsers(){
        return users.values();
    }

    public void deleteUser(String userName) {users.remove(userName);}

    public User getUser(String userName) {return users.get(userName);}

    public void clearUsers() {users.clear();}

    public void clear() {users.clear(); authTokens.clear();}
}
