package pl.maciejnalewajka.worktime;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class Dane  extends AppCompatActivity{
    public ArrayList<HashMap<String, Object>> users_list = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> masters_list = new ArrayList<HashMap<String, Object>>();
    public static HashMap<String, Object> my_hash = new HashMap<String, Object>();

    public Dane(ArrayList<HashMap<String, Object>> users_list, ArrayList<HashMap<String, Object>> masters_list) {
        this.users_list = users_list;
        this.masters_list = masters_list;
    }

    public ArrayList<HashMap<String, Object>> getUsers_list() {
        return users_list;
    }

    public void setUsers_list(ArrayList<HashMap<String, Object>> users_list) {
        this.users_list = users_list;
    }

    public ArrayList<HashMap<String, Object>> getMasters_list() {
        return masters_list;
    }

    public void setMasters_list(ArrayList<HashMap<String, Object>> masters_list) {
        this.masters_list = masters_list;
    }

    public HashMap<String, Object> getMy_hash() {
        return my_hash;
    }

    public void setMy_hash(HashMap<String, Object> my_hash) {
        Dane.my_hash = my_hash;
    }
}
