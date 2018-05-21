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
    ArrayList<HashMap<String, Object>> projects_list;
    ArrayList<HashMap<String, Object>> tasks_list;
//    ArrayList<HashMap<String, Object>> companys_list;
    String data = "";
    String users = "http://155.158.135.197/WorkTime/JSON.php?Users";
    String projects = "http://155.158.135.197/WorkTime/JSON.php?Projects";
    String tasks = "http://155.158.135.197/WorkTime/JSON.php?Tasks";
    String companys = "http://155.158.135.197/WorkTime/JSON.php?Companiers";

    @Override
    protected Void doInBackground(Void... voids) {
        // Wykonaj w tle
        users_list = new ArrayList<>();
        try {
            URL url = new URL(users);
            HttpURLConnection myConn = (HttpURLConnection) url.openConnection();
            InputStream resBody = myConn.getInputStream();
            BufferedReader bufread = new BufferedReader(new InputStreamReader(resBody));
            String line = "";
            while(line != null){
                line = bufread.readLine();
                data += line;
            }
            if (data != null) {

            }
        }
        catch (MalformedURLException e) {e.printStackTrace();}
        catch (IOException e)           {e.printStackTrace();}

        dane.users_list = users_list;
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}