package server;

import com.google.gson.Gson;
import dataaccess.MemoryDataAccess;
import exception.ResponseException;
import model.User;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Spark;
public class Server {
    private final UserService userService;
    public Server()
    {
        userService = new UserService(new MemoryDataAccess());
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.get("/user", this::getUser);
        Spark.post("/user", this::addUser);
        Spark.awaitInitialization();
        return Spark.port();
    }
    private Object addUser(Request req, Response res) throws ResponseException{
        var user = new Gson().fromJson(req.body(), User.class);
        user = userService.addUser(user);
        return new Gson().toJson(user);
    }
    private Object getUser(Request req, Response res) throws ResponseException {
        var userName = new Gson().fromJson(req.body(), String.class);
        return "nothing";
    }
    public static void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


}
