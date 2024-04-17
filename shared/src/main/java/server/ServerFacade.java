package server;

import com.google.gson.Gson;
import exception.ResponseException;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private final String serverUrl;
    public ServerFacade(String url) { serverUrl = url;}

    public void clear() throws ResponseException {
        var path = "/db";
        var response = this.makeRequest("DELETE", path, null, null, null);
    }
    public AuthToken register(User user) throws ResponseException {
        var path = "/user";
        return this.makeRequest("POST", path, user,null, AuthToken.class);
    }
    public AuthToken login(String username, String password) throws ResponseException {
        User user = new User(username, password, null);
        var path = "/session";
        return this.makeRequest("POST", path, user, null, AuthToken.class);
    }
    public void logout(AuthToken auth) throws ResponseException {
        var path = "/session";
        this.makeRequest("DELETE", path, null, auth.getAuth(), null);
    }
    public Game[] listGames(AuthToken auth) throws ResponseException {
        var path = "/game";
        record listGameResponse(Game[] game){
        }
        var response = this.makeRequest("GET", path, null, auth.getAuth(), listGameResponse.class).game();
        return response;
    }
    public gameID createGame(AuthToken auth, String gameInfo) throws ResponseException {
        Game game = new Game("0", null, null, gameInfo, null);
        var path = "/game";
        return this.makeRequest("POST", path, game, auth.getAuth(), gameID.class);
    }
    public Object joinGame(AuthToken auth, GameInfo gameInfo) throws ResponseException {
        var path = "/game";
        return this.makeRequest("PUT", path, gameInfo, auth.getAuth(), GameInfo.class);
    }
    private <T> T makeRequest(String method, String path, Object request, Object headerRequest, Class<T> responseClass) throws ResponseException{
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, headerRequest, http);
            //writeHeader(headerRequest, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            if(ex.getMessage().contains("403")){
                throw new ResponseException(500, "Error, already taken");
            }
            else if (ex.getMessage().contains("401")) {
                throw new ResponseException(500, "Error, unauthorized");
            }
            throw new ResponseException(500, ex.getMessage());
        }
    }
    private static void writeBody(Object request, Object headerRequest, HttpURLConnection http) throws IOException {
        if(headerRequest != null){
            http.setRequestProperty("Authorization", "" +  headerRequest);
        }
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }
    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }
    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }
    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}