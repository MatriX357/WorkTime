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

public class PobieranieTaskow extends AsyncTask<Void, Void, Void> {
    // Klasa pobierania tasków

    Dane dane;
    String tasks = "http://155.158.135.197/WorkTime/JSON.php?Tasks";
    String data = "";

    @Override
    protected Void doInBackground(Void... voids) {
        // Wykonaj w tle
        try {
            URL url = new URL(tasks);
            HttpURLConnection myConn = (HttpURLConnection) url.openConnection();
            InputStream resBody = myConn.getInputStream();
            BufferedReader bufread = new BufferedReader(new InputStreamReader(resBody));
            String line = "";
            while(line != null){
                line = bufread.readLine();
                data += line;
            }
            JSONArray JAr = new JSONArray(data);
            dane.tasks_list.clear();
            for(int i=0; i<JAr.length(); i++){
                JSONObject JOb = JAr.getJSONObject(i);
                String task_id = JOb.getString("task_id");
                String name = JOb.getString("name");
                String task = JOb.getString("task");
                String time = JOb.getString("time");
                String used_time = JOb.getString("used_time");
                String task_date = JOb.getString("task_date");
                String priority = JOb.getString("priority");
                String extraInfo = JOb.getString("extra_info");
                String project_id = JOb.getString("project_id");
                String user_id = JOb.getString("user_id");
                HashMap<String, Object> task_map = new HashMap<>();
                task_map.put("task_id", task_id);
                task_map.put("name", name);
                task_map.put("task", task);
                task_map.put("time", time);
                task_map.put("used_time", used_time);
                task_map.put("task_date", task_date);
                task_map.put("priority", priority);
                task_map.put("extra_info", extraInfo);
                task_map.put("project_id", project_id);
                task_map.put("user_id", user_id);
                dane.tasks_list.add(task_map);
            }
        }
        catch (JSONException | IOException e) {e.printStackTrace();}
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}