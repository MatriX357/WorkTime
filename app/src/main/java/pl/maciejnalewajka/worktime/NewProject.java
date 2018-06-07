package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class NewProject extends AppCompatActivity {
    private EditText name;
    private EditText client;
    private EditText platform;
    private EditText api;
    private EditText time;
    private EditText info;
    private EditText extraInfo;
    ManagerApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (ManagerApplication) getApplication();
        setContentView(R.layout.activity_nowy_projekt);
        name = findViewById(R.id.editText_n_name);
        client = findViewById(R.id.editText_n_client);
        platform = findViewById(R.id.editText_n_platform);
        api = findViewById(R.id.editText_n_api);
        time = findViewById(R.id.editText_n_time);
        info = findViewById(R.id.editText_n_info);
        extraInfo = findViewById(R.id.editText_n_extraInfo);
    }

    public void back(View view) {
        finish();
    }       // Przycisk wstecz

    public void add(View view) {
        if(name.getText().toString().equals("") | client.getText().toString().equals("") |
                platform.getText().toString().equals("") | api.getText().toString().equals("") |
                time.getText().toString().equals("")){
            Toast.makeText(this, "Uzupełnij wymagane pola!", Toast.LENGTH_SHORT).show();
        }
        else{
            String new_project_uuid = UUID.randomUUID().toString();
            app.addProject(new_project_uuid,name.getText().toString(),client.getText().toString(),platform.getText().toString(),api.getText().toString(),Integer.parseInt(time.getText().toString()),"22.12.19",info.getText().toString(),extraInfo.getText().toString(),ManagerApplication.user_uuid);
            Toast.makeText(this, "Dodano nowy projekt!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }               // Dodanie nowego projekru
}