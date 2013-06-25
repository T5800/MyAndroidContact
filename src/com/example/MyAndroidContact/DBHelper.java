package com.example.MyAndroidContact;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: hijack
 * Date: 13-6-25
 * Time: 上午9:44
 * To change this template use File | Settings | File Templates.
 */
public class DBHelper {


    private static ArrayList<Map<String, String>> arrayList = new ArrayList<Map<String, String>>(10);
    public static boolean save(User user) {
        HashMap<String, String> item = new HashMap<String, String>(2);
        item.put("name", user.getName());
        item.put("mobile", user.getMobile());
        item.put("email", user.getEmail());
        arrayList.add(item);
        return true;
    }

    public static ArrayList getAllUsers() {
        return arrayList;
    }

    public static ArrayList getUsers(String needSearch) {
        ArrayList list = new ArrayList(10);
        for (Map map : arrayList) {
            if (((String)map.get("name")).contains(needSearch) ||
                    ((String)map.get("mobile")).contains(needSearch)
                    )
                list.add(map);
        }
        return list;
    }

    public static User getUser(int id) {
        Map map = arrayList.get(id);
        User user = new User(
                (String)map.get("name"),
                (String)map.get("mobile"),
                (String)map.get("email")
        );
        return user;
    }

    public static boolean update(int id, User user) {
        HashMap item = new HashMap(4);
        item.put("name", user.getName());
        item.put("mobile", user.getMobile());
        item.put("email", user.getEmail());
        arrayList.set(id, item);
        return true;
    }

    public static boolean deleteMarked(ArrayList<Integer> ids) {
        for (int id : ids) {
            arrayList.remove(id);
        }
        return true;
    }
}
