package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class NowyProjekt extends AppCompatActivity {
    static EditText name, client, platform, api, time, info, extraInfo;
    static String new_project_uuid;
    Dane dane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowy_projekt);
        name = (EditText)findViewById(R.id.editText_n_name);
        client = (EditText)findViewById(R.id.editText_n_client);
        platform = (EditText)findViewById(R.id.editText_n_platform);
        api = (EditText)findViewById(R.id.editText_n_api);
        time = (EditText)findViewById(R.id.editText_n_time);
        info = (EditText)findViewById(R.id.editText_n_info);
        extraInfo = (EditText)findViewById(R.id.editText_n_extraInfo);
    }

    public void back(View view) {
        finish();
    }

    public void add(View view) {
        if(name.getText().toString().equals("") | client.getText().toString().equals("") |
                platform.getText().toString().equals("") | api.getText().toString().equals("") |
                time.getText().toString().equals("")){
            Toast.makeText(this, "Uzupełnij wymagane pola!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Dodano nowy projekt!", Toast.LENGTH_SHORT).show();
            new_project_uuid = UUID.randomUUID().toString();
            // Dodaj nowy projekt do danych.
        }
    }
}