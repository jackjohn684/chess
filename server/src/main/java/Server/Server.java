package server;

import com.google.gson.Gson;
import dataaccess.MemoryDataAccess;
import exception.ResponseException;
import model.ErrorMessage;
import model.LoginCredentials;
import model.User;
import service.*;
import spark.Request;
import spark.Response;
import spark.Spark;
import java.util.*;
public class Server {
    private final UserService userService;
    private final ClearService clearService;
    public Server()
    {
        userService = new UserService(new MemoryDataAccess());
        clearService = new ClearService (new MemoryDataAccess());
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.get("/user", this::listUsers);
        Spark.post("/user", this::register);
        Spark.delete("/user/:username", this::deleteUser);
        Spark.delete("/user", this::clearUsers);
        Spark.delete("/db", this::clear);
        Spark.post("/session", this::login);
        Spark.awaitInitialization();
        return Spark.port();
    }
    private Object register(Request req, Response res) throws ResponseException{
        var user = new Gson().fromJson(req.body(), User.class);
        var existing = userService.getUser(user.username());
        if (existing == null) {
            userService.addUser(user);
            var auth = userService.addAuthToken(user.username());
            return new Gson().toJson(auth);
        }
        else {
            res.status(403);
            return new Gson().toJson(new ErrorMessage("Error: already taken"));
        }
    }
    private Object login(Request req, Response res) throws ResponseException {
        LoginCredentials user = new Gson().fromJson(req.body(), LoginCredentials.class);
        var test = userService.getUser(user.username());
        if(test != null) {
            if (test.password().equals(user.password())) {
                var auth = userService.addAuthToken(user.username());
                res.status(200);
                return new Gson().toJson(auth);
            }
            else{
                res.status(401);
                return new Gson().toJson(new ErrorMessage("Error: unauthorized"));
            }
        }
        else {
            res.status(401);
            return new Gson().toJson(new ErrorMessage("Error: unauthorized"));
        }
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
    private Object clear(Request req, Response res) throws ResponseException {
        clearService.clear();
        res.status(204);
        return "";
    }

    private Object deleteUser(Request req, Response res) throws ResponseException {
        var userName = req.params(":username");
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
