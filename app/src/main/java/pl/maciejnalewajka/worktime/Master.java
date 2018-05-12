package pl.maciejnalewajka.worktime;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Master extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView lv;
    ArrayList<HashMap<String, String>> contactList;
    EditText name, client, platform, api, time, info, extraInfo;
    public static String name2, platform2;


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
        lv = (ListView)findViewById(R.id.listview_master_projects);
        contactList = new ArrayList<>();
        name2 = "Maciek";
        platform2 = "Lol";
        HashMap<String, String> contact = new HashMap<>();
        HashMap<String, String> contact2 = new HashMap<>();
        contact.put("mobile", name2);
        contact.put("email", platform2);
        contact2.put("mobile", name2);
        contact2.put("email", platform2);
        contactList.add(contact);
        contactList.add(contact);

        ListAdapter adapter = new SimpleAdapter(this, contactList,
                R.layout.list_projects, new String[]{ "email","mobile"},
                new int[]{R.id.l_nazwa, R.id.l_priorytet});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Intent intent_zad = new Intent(this, MasterDetale.class);
        startActivity(intent_zad);
    }

    public void nowyprojekt(View view) {
        Intent intent_pro = new Intent(this, NowyProjekt.class);
        startActivity(intent_pro);
    }

    public void profil(View view) {
        Intent intent_pro = new Intent(this, Profil.class);
        startActivity(intent_pro);
    }

    public void dodaj(View view) {
        if(name.getText().toString().equals("") | client.getText().toString().equals("") |
                platform.getText().toString().equals("") | api.getText().toString().equals("") |
                time.getText().toString().equals("")){
            Toast.makeText(this, "Uzupełnij wymagane pola!", Toast.LENGTH_SHORT).show();
        }

    }


}
