package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.UUID;

public class NewProject extends AppCompatActivity {
    private EditText name;
    private EditText client;
    private EditText platform;
    private EditText api;
    private EditText time;
    private EditText info;
    private EditText extraInfo;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowy_projekt);
        name = findViewById(R.id.editText_n_name);
        client = findViewById(R.id.editText_n_client);
        platform = findViewById(R.id.editText_n_platform);
        api = findViewById(R.id.editText_n_api);
        time = findViewById(R.id.editText_n_time);
        info = findViewById(R.id.editText_n_info);
        extraInfo = findViewById(R.id.editText_n_extraInfo);
        data = ManagerApplication.data;
    }

    public void back(View view) {
        finish();
    }       // Przycisk wstecz

    public void add(View view) {
        String myID = data.getMy_hash().get("user_id").toString();
        if(name.getText().toString().equals("") | client.getText().toString().equals("") |
                platform.getText().toString().equals("") | api.getText().toString().equals("") |
                time.getText().toString().equals("")){
            Toast.makeText(this, "Uzupełnij wymagane pola!", Toast.LENGTH_SHORT).show();
        }
        else{
            String new_project_uuid = UUID.randomUUID().toString();
            HashMap<String, Object> project_map = new HashMap<>();
            project_map.put("project_id", new_project_uuid);
            project_map.put("name", name.getText().toString());
            project_map.put("client", client.getText().toString());
            project_map.put("platform", platform.getText().toString());
            project_map.put("api", api.getText().toString());
            project_map.put("time", time.getText().toString());
            project_map.put("project_date", "22.12.19");
            project_map.put("info", info.getText().toString());
            project_map.put("extra_info", extraInfo.getText().toString());
            project_map.put("user_master_id", myID);
            data.projects_list.add(project_map);
            Toast.makeText(this, "Dodano nowy projekt!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }               // Dodanie nowego projekru
}