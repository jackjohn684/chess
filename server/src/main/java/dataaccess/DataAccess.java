package dataaccess;
import model.User;

import java.util.Collection;

public interface DataAccess {
    User addUser(User user);
    public Collection<User> listUsers();
    public User getUser(String userName);
    public void deleteUser(String userName);
    public void clearUsers();
}
