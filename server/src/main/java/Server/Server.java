package server;

import com.google.gson.Gson;
import dataaccess.MemoryDataAccess;
import exception.ResponseException;
import model.User;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Spark;
import java.util.*;
public class Server {
    private final UserService userService;
    public Server()
    {
        userService = new UserService(new MemoryDataAccess());
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.get("/user", this::listUsers);
        Spark.post("/user", this::addUser);
        Spark.delete("/user/:userName", this::deleteUser);
        Spark.delete("/user", this::clearUsers);
        Spark.awaitInitialization();
        return Spark.port();
    }
    private Object addUser(Request req, Response res) throws ResponseException{
        var user = new Gson().fromJson(req.body(), User.class);
        user = userService.addUser(user);
        return new Gson().toJson(user);
    }
    private Object listUsers(Request req, Response res) throws ResponseException {
        res.type("application/json");
        var list = userService.listUsers().toArray();
        return new Gson().toJson(Map.of("user", list));
    }

    private Object clearUsers(Request req, Response res) throws ResponseException {
        userService.clearUsers();
        res.status(204);
        return "";
    }

    private Object deleteUser(Request req, Response res) throws ResponseException {
        var userName = req.params(":userName");
        var user = userService.getUser(userName);
        if (user != null) {
            userService.deleteUser(userName);
            res.status(204);
        }
        else {
            res.status(404);
        }
        return "";
    }

    public static void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


}
