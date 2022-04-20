package com.example.mapsedwing.ModelFlickr;

public class Photo {
    public String id;
    public String server;
    public String secret;
    public String owner;
    public String title;

    public Photo(String id, String server, String secret, String owner, String title) {
        this.id = id;
        this.server = server;
        this.secret = secret;
        this.owner = owner;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
