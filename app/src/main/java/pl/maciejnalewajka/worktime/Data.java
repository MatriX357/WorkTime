package pl.maciejnalewajka.worktime;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class Data extends AppCompatActivity{
    static ArrayList<HashMap<String, Object>> users_list = new ArrayList<>();
    static ArrayList<HashMap<String, Object>> projects_list = new ArrayList<>();
    static ArrayList<HashMap<String, Object>> tasks_list = new ArrayList<>();
    private static HashMap<String, Object> my_hash = new HashMap<>();

    public static ArrayList<HashMap<String, Object>> getUsers_list() {
        return users_list;
    }

    public static void setUsers_list(ArrayList<HashMap<String, Object>> users_list) {
        Data.users_list = users_list;
    }

    public static ArrayList<HashMap<String, Object>> getProjects_list() {
        return projects_list;
    }

    public static void setProjects_list(ArrayList<HashMap<String, Object>> projects_list) {
        Data.projects_list = projects_list;
    }

    public static ArrayList<HashMap<String, Object>> getTasks_list() {
        return tasks_list;
    }

    public static void setTasks_list(ArrayList<HashMap<String, Object>> tasks_list) {
        Data.tasks_list = tasks_list;
    }

    public HashMap<String, Object> getMy_hash() {
        return my_hash;
    }

    public void setMy_hash(HashMap<String, Object> my_hash) {
        Data.my_hash = my_hash;
    }


}
