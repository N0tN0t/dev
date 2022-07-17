package main.entities;

import java.util.Set;

public enum Permission {
    USER("user:write"),
    MODERATE("user:moderate");

    private final String permissions;

    Permission(String permissions) {
        this.permissions = permissions;
    }

    public String getPermission() {
        return permissions;
    }
}
