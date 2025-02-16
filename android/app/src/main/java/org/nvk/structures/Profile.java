package org.nvk.structures;

public class Profile {
    public String username;
    public boolean isOwner;

    public Profile() {
    }

    public Profile(String username, boolean isOwner) {
        this.username = username;
        this.isOwner = isOwner;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isRenter() {
        return !isOwner;
    }
}
