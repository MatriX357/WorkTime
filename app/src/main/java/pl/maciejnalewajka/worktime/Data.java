package pl.maciejnalewajka.worktime;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class Data extends AppCompatActivity{
    ArrayList<HashMap<String, Object>> users_list = new ArrayList<>();
    ArrayList<HashMap<String, Object>> projects_list = new ArrayList<>();
    ArrayList<HashMap<String, Object>> tasks_list = new ArrayList<>();
    private static HashMap<String, Object> my_hash = new HashMap<>();

    public HashMap<String, Object> getMy_hash() {
        return my_hash;
    }

    public void setMy_hash(HashMap<String, Object> my_hash) {
        Data.my_hash = my_hash;
    }


}
