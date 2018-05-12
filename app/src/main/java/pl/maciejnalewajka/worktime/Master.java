package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Master extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView lv;
    ArrayList<HashMap<String, String>> contactList;
    static String name2, platform2, procent;
    ProgressBar progresbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);


        lv = (ListView)findViewById(R.id.listview_m);
        progresbar = (ProgressBar)findViewById(R.id.l_progressBar);
        contactList = new ArrayList<>();
        name2 = "Maciek";
        platform2 = "Wysoki";
        procent = "26%";
        HashMap<String, String> contact = new HashMap<>();
        HashMap<String, String> contact2 = new HashMap<>();
        contact.put("name", name2);
        contact.put("priority", platform2);
        contact.put("procent", procent);
        contact2.put("name", name2);
        contact2.put("priority", platform2);
        contact2.put("procent", procent);
        contactList.add(contact);
        contactList.add(contact);

        ListAdapter adapter = new SimpleAdapter(this, contactList,
                R.layout.list_projects, new String[]{ "name","priority", "procent"},
                new int[]{R.id.l_nazwa, R.id.l_priorytet, R.id.l_procent});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Intent intent_osoba = new Intent(this, MasterOsoba.class);
        startActivity(intent_osoba);
//        progresbar =lv.getSelectedView().findViewById(R.id.l_progressBar);
//        progresbar.setProgress(70);
    }

    public void new_project(View view) {
        Intent intent_new_project = new Intent(this, NowyProjekt.class);
        startActivity(intent_new_project);
    }

    public void profil(View view) {
        Intent intent_profil = new Intent(this, Profil.class);
        startActivity(intent_profil);
    }
}
