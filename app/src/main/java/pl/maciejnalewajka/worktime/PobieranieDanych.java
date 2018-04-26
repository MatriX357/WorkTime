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

public class PobieranieDanych extends AsyncTask<Void, Void, Void> {
    //Klasa pobierania danych

    String data = "";
    String www = "strona www";
    @Override
    protected Void doInBackground(Void... voids) {

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

        }
        catch (MalformedURLException e) {e.printStackTrace();}
        catch (IOException e)           {e.printStackTrace();}
        //catch (JSONException e)         {e.printStackTrace();}
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private static double mid;

    public double getMid() { return mid; }

    public void setMid(double mid) { PobieranieDanych.mid = mid; }
}