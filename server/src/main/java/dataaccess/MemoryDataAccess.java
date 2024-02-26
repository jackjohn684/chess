package dataaccess;
import model.User;
import java.util.HashMap;
public class MemoryDataAccess implements DataAccess {
    private int nextId = 1;
    final private HashMap<Integer, User> users = new HashMap<>();
    public User addUser(User user) {
        user = new User(user.userName(), user.password(), user.email());
        users.put(nextId++, user);
        return user;
    }
}
