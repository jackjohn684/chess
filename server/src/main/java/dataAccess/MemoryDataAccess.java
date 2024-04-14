package dataAccess;

import chess.ChessGame;
import model.AuthToken;
import model.Game;
import model.User;
import model.gameID;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
public class MemoryDataAccess implements DataAccess {

    final private HashMap<String, User> users = new HashMap<>();
    final private HashMap<String, AuthToken> authTokens = new HashMap<>();
    public User addUser(User user) throws SQLException, DataAccessException {
        Connection conn = DatabaseManager.getConnection();
        try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, user.username());
            preparedStatement.setString(2, user.password());
            preparedStatement.setString(3, user.email());
            preparedStatement.executeUpdate();
        }
        return user;
    }
    public AuthToken addAuthToken(String username) throws SQLException, DataAccessException {
        Connection conn = DatabaseManager.getConnection();
        var auth = new AuthToken(username);
        try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (authToken, username) VALUES(?, ?)")) {
            preparedStatement.setString(1, auth.getAuth());
            preparedStatement.setString(2, auth.getUsername());
            preparedStatement.executeUpdate();
        }
        return auth;
    }
    public void deleteAuthToken(String auth) {
        authTokens.remove(auth);
    }
    public gameID createGame(String auth, String gameName) throws DataAccessException, SQLException {
        Random rnd = new Random();
        int randomNumber = 1000 + rnd.nextInt(9000);
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("INSERT INTO game (gameName, gameID) VALUES(?,?)")){
            preparedStatement.setString(1, gameName);
            preparedStatement.setInt(2,randomNumber);
            preparedStatement.executeUpdate();
        }
        var myGameId = new gameID(randomNumber);
        return myGameId;
    }
    public String makeGame(Game game) throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("INSERT INTO game (gameName, whiteUsername, blackUsername, gameID) VALUES(?,?,?,?)")){
            preparedStatement.setString(1, game.gameName());
            preparedStatement.setString(2, game.whiteUsername());
            preparedStatement.setString(3, game.blackUsername());
            preparedStatement.setInt(4, game.gameID());
            preparedStatement.executeUpdate();
        }
        return "yee";
    }
    public Collection<User> listUsers(){
        return users.values();
    }
    public Collection<Game> listGames() throws DataAccessException, SQLException {
        ArrayList<Game> games = new ArrayList<Game>();
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName from game")){
            try(var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    var gameID = rs.getInt("gameID");
                    var whiteUsername = rs.getString("whiteUsername");
                    var blackUsername = rs.getString("blackUsername");
                    var gameName = rs.getString("gameName");
                   Game game = new Game(gameID, whiteUsername, blackUsername, gameName, new ChessGame());
                   games.add(game);
                }
            }
        }
        return games;
    }

    public void deleteUser(String username) throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try (var preparedStatement = conn.prepareStatement("DELETE from user where username = ?")){
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        }
    }
    public void deleteGame(int gameID) throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try (var preparedStatement = conn.prepareStatement("DELETE from game where gameID = ?")){
            preparedStatement.setInt(1, gameID);
            preparedStatement.executeUpdate();
        }
    }
    public User getUser(String username) throws DataAccessException, SQLException {
        User user = null;
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("SELECT username, password, email from user WHERE username = ?")) {
            preparedStatement.setString(1, username);
            try (var rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    var otherUsername = rs.getString("username");
                    var password = rs.getString("password");
                    var email = rs.getString("email");
                    user = new User(otherUsername, password, email);
                }
            }
        }
        return user;
    }
    public AuthToken getAuthToken(String auth) throws DataAccessException, SQLException {
        AuthToken auth1 = null;
        Connection conn = DatabaseManager.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT authToken, username from auth WHERE authToken=?")) {
            preparedStatement.setString(1, auth);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    var authToken = rs.getString("authToken");
                    var username = rs.getString("username");
                    auth1 = new AuthToken(username, authToken);
                }
            }
        }
        return auth1;
    }
    public void clearUsers() throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("DROP TABLE auth;")){
            preparedStatement.executeUpdate();
        }
        try(var preparedStatement = conn.prepareStatement("DROP TABLE user;")){
            preparedStatement.executeUpdate();
        }
    }
    public void clearGames() throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("DROP TABLE game")) {
            preparedStatement.executeUpdate();
        }
    }

    public void clear() throws SQLException, DataAccessException {
        clearUsers();
        clearGames();
    }
    public Game getGame(int gameID) throws DataAccessException, SQLException {
        Game game = null;
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName from game WHERE gameID = ?")){
            preparedStatement.setInt(1, gameID);
            try(var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    var whiteUsername = rs.getString("whiteUsername");
                    var blackUsername = rs.getString("blackUsername");
                    var gameName = rs.getString("gameName");

                   game = new Game(gameID, whiteUsername, blackUsername, gameName, new ChessGame());
                }
            }
        }
        return game;
    }


    public String joinGame(int gameID, String playerColor, String username) throws SQLException, DataAccessException {
        var oldGame = getGame(gameID);
        if (oldGame == null || oldGame.gameID() != gameID){
            return  "400";
        }
        Game newGame = null;
        if (playerColor == null) {
            return "";
        } else if (playerColor.equals("WHITE")) {
            if (oldGame.whiteUsername() == null) {
                newGame = new Game(oldGame.gameID(), username, oldGame.blackUsername(), oldGame.gameName(), oldGame.game());
            }
            else {
                return "403";
            }
        } else if (playerColor.equals("BLACK")) {
            if (oldGame.blackUsername() == null) {
                newGame = new Game(oldGame.gameID(), oldGame.whiteUsername(), username, oldGame.gameName(), oldGame.game());
            }
            else {
                return "403";
            }
        }
        deleteGame(gameID);
        assert newGame != null;
        makeGame(newGame);
        return "";

    }
}
