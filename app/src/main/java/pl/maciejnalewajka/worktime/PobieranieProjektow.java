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

public class PobieranieProjektow extends AsyncTask<Void, Void, Void> {
    // Klasa pobierania projektów

    Dane dane;
    String projects = "http://155.158.135.197/WorkTime/JSON.php?Projects";
    String data = "";

    @Override
    protected Void doInBackground(Void... voids) {
        // Wykonaj w tle
        try {
            URL url = new URL(projects);
            HttpURLConnection myConn = (HttpURLConnection) url.openConnection();
            InputStream resBody = myConn.getInputStream();
            BufferedReader bufread = new BufferedReader(new InputStreamReader(resBody));
            String line = "";
            while(line != null){
                line = bufread.readLine();
                data += line;
            }
            JSONArray JAr = new JSONArray(data);
            dane.projects_list.clear();
            for(int i=0; i<JAr.length(); i++){
                JSONObject JOb = JAr.getJSONObject(i);
                String project_id = JOb.getString("project_id");
                String name = JOb.getString("name");
                String client = JOb.getString("client");
                String platform = JOb.getString("platform");
                String api = JOb.getString("api");
                String time = JOb.getString("time");
                String project_date = JOb.getString("project_date");
                String info = JOb.getString("info");
                String extraInfo = JOb.getString("extra_info");
                String user_master_id = JOb.getString("user_master_id");
                HashMap<String, Object> project_map = new HashMap<>();
                project_map.put("project_id", project_id);
                project_map.put("name", name);
                project_map.put("client", client);
                project_map.put("platform", platform);
                project_map.put("api", api);
                project_map.put("time", time);
                project_map.put("project_date", project_date);
                project_map.put("info", info);
                project_map.put("extra_info", extraInfo);
                project_map.put("user_master_id", user_master_id);
                dane.projects_list.add(project_map);
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