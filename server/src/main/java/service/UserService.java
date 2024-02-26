package service;
import dataaccess.DataAccess;
import exception.ResponseException;
import model.User;
public class UserService {
    private final DataAccess dataAccess;
    public UserService(DataAccess dataAccess) { this.dataAccess = dataAccess;}
    public User addUser(User user) throws ResponseException {
        return dataAccess.addUser(user);
    }

    public Object getUser() {
        return "nothing"; // dataAccess.getUser();
    }
}
