package dataaccess;
import model.AuthToken;
import model.User;

import java.util.Collection;

public interface DataAccess {
    User addUser(User user);
    public Collection<User> listUsers();
    public User getUser(String userName);
    public void deleteUser(String userName);
    public void deleteAuthToken(String username);
    public void clearUsers();
    public AuthToken addAuthToken(String username);

    public AuthToken getAuthToken(String username);


    public void clear();
}
