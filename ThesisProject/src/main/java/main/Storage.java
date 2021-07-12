package main;

import main.Entities.Posts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {
    private static int cid = 1;
    private  static HashMap<Integer, Posts> posts = new HashMap<Integer, Posts>();

    public static List<Posts> getPosts() {
        ArrayList<Posts> courseList = new ArrayList<Posts>();
        courseList.add((Posts) posts.values());
        return courseList;
    }
    public static Posts addPost(Posts tsk) {
        int id = cid;
        tsk.setId(id);
        cid++;
        return tsk;
    }
    public static Posts putPost(int id, Posts tsk) {
        return posts.put(id,tsk);
    }
    public static int deletePost(int id) {
        posts.remove(id);
        return id;
    }
    public static HashMap<Integer, Posts> deleteAllPosts() {
        posts = new HashMap<Integer, Posts>();
        return posts;
    }
    public static Posts getPost(int Cid) {
        if(posts.containsKey(Cid)) {
            return posts.get(Cid);
        }
        return null;
    }
}
