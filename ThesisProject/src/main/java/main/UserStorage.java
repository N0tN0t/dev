package main;

import main.Entities.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserStorage {
    private static int cid = 1;
    private  static HashMap<Integer, Users> users = new HashMap<Integer, Users>();

    public static List<Users> getUsers() {
        ArrayList<Users> courseList = new ArrayList<Users>();
        courseList.add((Users) users.values());
        return courseList;
    }
    public static Users addUser(Users tsk) {
        int id = cid;
        tsk.setId(id);
        cid++;
        return tsk;
    }
    public static Users putUsers(int id, Users tsk) {
        return users.put(id,tsk);
    }
    public static int deleteUser(int id) {
        users.remove(id);
        return id;
    }
    public static HashMap<Integer, Users> deleteAllUsers() {
        users = new HashMap<Integer, Users>();
        return users;
    }
    public static Users getUser(int Cid) {
        if(users.containsKey(Cid)) {
            return users.get(Cid);
        }
        return null;
    }
}
