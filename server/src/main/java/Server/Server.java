package server;

import com.google.gson.Gson;
import dataaccess.MemoryDataAccess;
import exception.ResponseException;
import model.*;
import service.ClearService;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;
public class Server {
    private final UserService userService;
    private final ClearService clearService;
    private final GameService gameService;
    public Server()
    {
        userService = new UserService(new MemoryDataAccess());
        clearService = new ClearService (new MemoryDataAccess());
        gameService = new GameService (new MemoryDataAccess());
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
        Spark.delete("/session", this::logout);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
        Spark.get("/game", this::listGames);
        Spark.awaitInitialization();
        return Spark.port();
    }
    private Object register(Request req, Response res) throws ResponseException{
        var user = new Gson().fromJson(req.body(), User.class);
        var existing = userService.getUser(user.username());
        if (user.username() == null || user.password() == null || user.email() == null){
            res.status(400);
            return new Gson().toJson(new ErrorMessage("Error: bad request"));
        }
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

    private Object createGame(Request req, Response res) throws ResponseException {
        var auth = req.headers("authorization");
        var gameName = new Gson().fromJson(req.body(), GameName.class);
        var user = userService.getAuthToken(auth);
        if (user != null)
        {
            res.status(200);
            var myGameId = gameService.createGame(auth, gameName.gameName());
            return new Gson().toJson(myGameId);
        }
        else{
            res.status(401);
            return new Gson().toJson(new ErrorMessage("Error"));
        }
    }
    private Object joinGame(Request req, Response res) throws ResponseException {
        String statusCode = "";
        var auth = req.headers("authorization");
        var gameInfo = new Gson().fromJson(req.body(), GameInfo.class);
        var user = userService.getAuthToken(auth);
        if(user != null){
            statusCode = gameService.joinGame(gameInfo.gameID(), gameInfo.playerColor(), user.getUsername());
            if (statusCode.equals("403"))
            {
                res.status(403);
                return new Gson().toJson(new ErrorMessage("ERROR"));
            }
            else if (statusCode.equals("400"))
            {
                res.status(400);
                return new Gson().toJson(new ErrorMessage("ERROR"));
            } else {
            res.status(200);
            return new Gson().toJson(gameInfo);
            }
        }else {
            res.status(401);
            return new Gson().toJson(new ErrorMessage("Error"));
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
    private Object listGames(Request req, Response res) throws ResponseException {
        var auth = req.headers("authorization");
        var user = userService.getAuthToken(auth);
        res.type("application/json");
        var list = gameService.listGames().toArray();
        return new Gson().toJson(Map.of("games", list));
    }
    private Object logout(Request req, Response res) throws ResponseException {
        var auth = req.headers("authorization");
         var user = userService.getAuthToken(auth);
         if (user != null) {
             userService.deleteAuthToken(auth);
             res.status(200);
             return "";
         }
         else {
             res.status(401);
             return new Gson().toJson(new ErrorMessage("Error"));
         }
    }

    private Object clearUsers(Request req, Response res) throws ResponseException {
        userService.clearUsers();
        res.status(204);
        return "";
    }
    private Object clear(Request req, Response res) throws ResponseException {
        clearService.clear(userService,gameService);
        res.status(200);
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
