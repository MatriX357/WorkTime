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
import java.net.URL;
import java.util.HashMap;

class UsersDownload extends AsyncTask<Void, Void, Void> {
    // Klasa pobierania userów

    private String data = "";

    @Override
    protected Void doInBackground(Void... voids) {
        // Wykonaj w tle
        try {
            String users = "http://155.158.135.197/WorkTime/JSON.php?Users";
            URL url = new URL(users);
            HttpURLConnection myConn = (HttpURLConnection) url.openConnection();
            InputStream resBody = myConn.getInputStream();
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(resBody));
            String line = "";
            while(line != null){
                line = buffRead.readLine();
                data = String.format("%s%s", data, line);
            }
            JSONArray JAr = new JSONArray(data);
            Data.users_list.clear();
            for(int i=0; i<JAr.length(); i++){
                JSONObject JOb = JAr.getJSONObject(i);
                String user_id = JOb.getString("user_id");
                String name = JOb.getString("name");
                String email = JOb.getString("email");
                String password = JOb.getString("password");
                String phone = JOb.getString("phone");
                String type = JOb.getString("type");
                String company_id = JOb.getString("company_id");
                HashMap<String, Object> user_map = new HashMap<>();
                user_map.put("user_id", user_id);
                user_map.put("name", name);
                user_map.put("email", email);
                user_map.put("password", password);
                user_map.put("phone", phone);
                user_map.put("type", type);
                user_map.put("company_id", company_id);
                Data.users_list.add(user_map);
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