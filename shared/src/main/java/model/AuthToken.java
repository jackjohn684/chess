package model;

import com.google.gson.Gson;

import java.util.UUID;

public class AuthToken {
   private String username;
    private String authToken;
    public AuthToken(String username) {
        this.username = username;
        this.authToken = UUID.randomUUID().toString();
    }
    public String getUsername()
    {
        return username;
    }
    public String getAuth(){
        return authToken;
    }
    public String toString() { return new Gson().toJson(this); }
}