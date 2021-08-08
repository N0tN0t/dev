package main.api.response;

import main.entities.Posts;
import main.entities.Users;

import java.util.ArrayList;

public class PostResponse {
    private int count;
    private ArrayList posts = new ArrayList();
    private ArrayList users;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList getPosts() {
        return posts;
    }

    public ArrayList addPosts(Posts posts1, int timestamp,Users users1,int likeCount,int dislikeCount,int commentCount) {
        ArrayList postToSend = new ArrayList();
        postToSend.add(posts1.getId());
        postToSend.add(timestamp);
        postToSend.add(setUsers(users1));
        postToSend.add(posts1.getTitle());
        postToSend.add(posts1.getText());
        postToSend.add(likeCount);
        postToSend.add(dislikeCount);
        postToSend.add(commentCount);
        postToSend.add(posts1.getView_count());
        posts.add(postToSend);
        return posts;
    }
    private ArrayList setUsers(Users users1) {
        users.add(users1.getId());
        users.add(users1.getName());
        return users;
    }
}
