package server;

import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthToken;
import model.User;

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

    public User register(User user) throws ResponseException {
        var path = "/user";
        return this.makeRequest("POST", path, user, User.class);
    }
    public User login(User user) throws ResponseException {
        var path = "/session";
        return this.makeRequest("POST", path, user, User.class);
    }
    public void logout(AuthToken authToken) throws ResponseException {
        var path = "/session";

        this.makeRequest("DELETE", path, authToken, null);
    }
    public User[] listUsers() throws ResponseException {
        var path = "/user";
        record listUserResponse(User[] user) {
        }
        var response = this.makeRequest("GET", path, null, listUserResponse.class);
        return response.user();
    }
    public void deleteUser(String userName) throws ResponseException {
        var path = String.format("/user/%s", userName);
        this.makeRequest("DELETE", path, null, null);
    }
    public void clearUsers() throws ResponseException {
        var path = "/user";
        this.makeRequest("DELETE", path, null, null);
    }
    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException{
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
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

