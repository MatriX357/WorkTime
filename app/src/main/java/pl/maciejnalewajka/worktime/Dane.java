package pl.maciejnalewajka.worktime;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class Dane  extends AppCompatActivity{
    public static ArrayList<HashMap<String, Object>> users_list = new ArrayList<HashMap<String, Object>>();
    public static ArrayList<HashMap<String, Object>> projects_list = new ArrayList<HashMap<String, Object>>();
    public static ArrayList<HashMap<String, Object>> tasks_list = new ArrayList<HashMap<String, Object>>();
    public static HashMap<String, Object> my_hash = new HashMap<String, Object>();


    public HashMap<String, Object> getMy_hash() {
        return my_hash;
    }

    public void setMy_hash(HashMap<String, Object> my_hash) {
        Dane.my_hash = my_hash;
    }



}
