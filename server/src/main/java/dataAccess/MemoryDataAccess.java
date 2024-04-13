package dataAccess;

import chess.ChessGame;
import model.AuthToken;
import model.Game;
import model.User;
import model.gameID;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
public class MemoryDataAccess implements DataAccess {

    final private HashMap<String, User> users = new HashMap<>();
    final private HashMap<String, AuthToken> authTokens = new HashMap<>();
    final private HashMap<Integer, Game> games = new HashMap<>();
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
    public gameID createGame(String auth, String gameName){
        Random rnd = new Random();
        int randomNumber = 1000 + rnd.nextInt(9000);
        var myGameId = new gameID(randomNumber);
        games.put(myGameId.gameID(), new Game(myGameId.gameID(), null, null, gameName, new ChessGame()) );
        return myGameId;
    }

    public Collection<User> listUsers(){
        return users.values();
    }
    public Collection<Game> listGames() { return games.values();}

    public void deleteUser(String username) throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try (var preparedStatement = conn.prepareStatement("DELETE from user where username = ?")){
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        }
    }
    public User getUser(String username) throws DataAccessException, SQLException {
        User user = null;
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("SELECT username, password, email from user WHERE username = ?")) {
            preparedStatement.setString(1, username);
            try (var rs = preparedStatement.executeQuery()) {
                if(rs.getFetchSize() > 0) {
                    var otherUsername = rs.getString("username");
                    var password = rs.getString("password");
                    var email = rs.getString("email");
                    user = new User(otherUsername, password, email);
                }
            }
        }
        return user;
    }
    public AuthToken getAuthToken(String auth) { return authTokens.get(auth);}
    public void clearUsers() throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("DROP TABLE auth; DROP TABLE user;")){
            preparedStatement.executeUpdate();
        }
    }
    public void clearGames(){games.clear();}

    public void clear() {
        users.clear();
        authTokens.clear();
        games.clear();
    }
    public Game getGame(int gameID){
        return games.get(gameID);
    }

    public String joinGame(int gameID, String playerColor, String username) {
        var oldGame = games.get(gameID);
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
        games.remove(gameID);
        assert newGame != null;
        games.put(newGame.gameID(), newGame);
        return "";

    }
}
