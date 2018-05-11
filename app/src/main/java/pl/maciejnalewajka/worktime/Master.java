package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Master extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> data;
    EditText name, client, platform, api, time, info, extraInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        name = (EditText)findViewById(R.id.editText_n_name);
        client = (EditText)findViewById(R.id.editText_n_client);
        platform = (EditText)findViewById(R.id.editText_n_platform);
        api = (EditText)findViewById(R.id.editText_n_api);
        time = (EditText)findViewById(R.id.editText_n_time);
        info = (EditText)findViewById(R.id.editText_n_info);
        extraInfo = (EditText)findViewById(R.id.editText_n_extraInfo);
    }

    @Override
    public void onBackPressed() {
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void nowyprojekt(View view) {
        setContentView(R.layout.activity_nowy_projekt);
    }

    public void dodaj(View view) {
        if(name.getText().toString().equals("") | client.getText().toString().equals("") |
                platform.getText().toString().equals("") | api.getText().toString().equals("") |
                time.getText().toString().equals("")){
            Toast.makeText(this, "Uzupełnij wymagane pola!", Toast.LENGTH_SHORT).show();
        }

    }
    public void anuluj(View view){
        setContentView(R.layout.activity_master);
    }

    public void profil(View view) {
        Intent intent_pro = new Intent(this, Profil.class);
        startActivity(intent_pro);
    }


}
