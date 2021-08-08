package main.api.response;

import main.entities.Users;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CheckResponse {
    private boolean result = false;
    private ArrayList users;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ArrayList getUsers() {
        return users;
    }

    public ArrayList setUsers(Users user,boolean moderation, int moderationCount, boolean settings) {
        users.add(user.getId());
        users.add(user.getName());
        users.add(user.getPhoto());
        users.add(user.getEmail());
        users.add(moderation);
        users.add(moderationCount);
        users.add(settings);
        return users;
    }
}

