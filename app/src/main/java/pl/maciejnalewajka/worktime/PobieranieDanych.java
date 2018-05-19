package pl.maciejnalewajka.worktime;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class PobieranieDanych extends AsyncTask<Void, Void, Void> {
    // Klasa pobierania danych
    Dane dane;
    ArrayList<HashMap<String, Object>> users_list;
    ArrayList<HashMap<String, Object>> masters_list;
    String data = "";
    String www = "https://olszewski_nie_zrobil_sesrvera.com";

    @Override
    protected Void doInBackground(Void... voids) {
        // Wykonaj w tle
        users_list = new ArrayList<>();
        masters_list = new ArrayList<>();
        try {
            URL url = new URL(www);
            HttpURLConnection myConn = (HttpURLConnection) url.openConnection();
            InputStream resBody = myConn.getInputStream();
            BufferedReader bufread = new BufferedReader(new InputStreamReader(resBody));
            String line = "";
            while(line != null){
                line = bufread.readLine();
                data += line;
            }
            if (data != null) {
                try {
                    JSONObject jsonObj = new JSONObject(data);
                    JSONObject company = jsonObj.getJSONObject("company");
                    JSONArray jsonAr_users = company.getJSONArray("users");
                    for (int i=0; i<jsonAr_users.length(); i++) {
                        JSONObject users = jsonAr_users.getJSONObject(i);
                        String name = users.getString("name");
                        String email = users.getString("email");
                        String password = users.getString("password");
                        int phone = users.getInt("phone");
                        String user_id = users.getString("user_id");
                        JSONArray user_projects = users.getJSONArray("user_projects");
                        HashMap<String, Object> user_projects_map = null;
                        for (int k = 0; k < user_projects.length(); k++) {
                            JSONObject pro = user_projects.getJSONObject(k);
                            String project_id = pro.getString("project_id");
                            JSONArray user_tasks = pro.getJSONArray("user_tasks");
                            HashMap<String, String> user_tasks_map = null;
                            for (int j = 0; j < user_tasks.length(); j++) {
                                JSONObject tas = user_tasks.getJSONObject(j);
                                String task_id = tas.getString("task_id");
                                user_tasks_map = new HashMap<>();
                                user_tasks_map.put("task_id", task_id);
                            }
                            user_projects_map = new HashMap<>();
                            user_projects_map.put("project_id", project_id);
                            user_projects_map.put("user_tasks_map", user_tasks_map);
                        }
                        HashMap<String, Object> users_map = new HashMap<>();
                        users_map.put("name", name);
                        users_map.put("email", email);
                        users_map.put("password", password);
                        users_map.put("phone", phone);
                        users_map.put("user_id", user_id);
                        users_map.put("user_projects_map", user_projects_map);
                        users_list.add(users_map);
                    }


                    JSONArray jsonAr_masters = jsonObj.getJSONArray("users_master");
                    HashMap<String, Object> masters_map = null;
                    for (int i=0; i<jsonAr_masters.length(); i++){
                        JSONObject master = jsonAr_masters.getJSONObject(i);
                        String name = master.getString("name");
                        String email = master.getString("email");
                        String password = master.getString("password");
                        int phone = master.getInt("phone");
                        String user_master_id = master.getString("user_master_id");
                        JSONArray projects = master.getJSONArray("projects");
                        HashMap<String, Object> projects_map = null;
                        for (int l=0; l<projects.length(); l++){
                            JSONObject pro = projects.getJSONObject(l);
                            String name_pro = pro.getString("name");
                            String client = pro.getString("client");
                            String platform = pro.getString("platform");
                            String api = pro.getString("api");
                            int time = pro.getInt("time");
                            String project_data = pro.getString("project_data");
                            String info = pro.getString("info");
                            String extraInfo = pro.getString("extraInfo");
                            String project_id = pro.getString("project_id");
                            String user_master_id_pro = pro.getString("user_master_id");
                            JSONArray tasks = pro.getJSONArray("tasks");
                            HashMap<String, Object> tasks_map = null;
                            for (int p=0; p<tasks.length(); p++){
                                JSONObject tas = tasks.getJSONObject(p);
                                String name_tas = tas.getString("name");
                                String task = tas.getString("task");
                                int time_tas = tas.getInt("time");
                                int used_time = tas.getInt("used_time");
                                String task_data = tas.getString("task_data");
                                String priority = tas.getString("priority");
                                String extraInfo_tas = tas.getString("extraInfo");
                                String task_id = tas.getString("task_id");
                                String user_id = tas.getString("user_id");
                                tasks_map = new HashMap<>();
                                tasks_map.put("name", name_tas);
                                tasks_map.put("task", task);
                                tasks_map.put("time_tas", time_tas);
                                tasks_map.put("used_time", used_time);
                                tasks_map.put("task_data", task_data);
                                tasks_map.put("priority", priority);
                                tasks_map.put("extraInfo", extraInfo_tas);
                                tasks_map.put("task_id", task_id);
                                tasks_map.put("user_id", user_id);
                            }
                            projects_map = new HashMap<>();
                            projects_map.put("name_pro", name_pro);
                            projects_map.put("client", client);
                            projects_map.put("platform", platform);
                            projects_map.put("api", api);
                            projects_map.put("time", time);
                            projects_map.put("project_data", project_data);
                            projects_map.put("info", info);
                            projects_map.put("extraInfo", extraInfo);
                            projects_map.put("project_id", project_id);
                            projects_map.put("user_master_id_pro", user_master_id_pro);
                            projects_map.put("tasks_map", tasks_map);
                        }
                        masters_map.put("name", name);
                        masters_map.put("email", email);
                        masters_map.put("password", password);
                        masters_map.put("phone", phone);
                        masters_map.put("user_master_id", user_master_id);
                        masters_map.put("projects_map", projects_map);
                        masters_list.add(masters_map);
                    }
                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (MalformedURLException e) {e.printStackTrace();}
        catch (IOException e)           {e.printStackTrace();}

        dane.users_list = users_list;
        dane.masters_list = masters_list;
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}