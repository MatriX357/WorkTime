package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class Master extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView lv;
    ArrayList<String> projekty_lista;
    ArrayList<Elementy> data;
    ArrayAdapter<Elementy> adapter;
    Dane dane;
    String myID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        lv = findViewById(R.id.listview_m);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dane = new Dane();
        myID = dane.getMy_hash().get("user_id").toString();
        projekty();
        elementy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MasterZadania.id = projekty_lista.get(position);
        NoweZadanie.idP = projekty_lista.get(position);
        MasterOsoba.idP = projekty_lista.get(position);
        Intent intent_master_zadania = new Intent(this, MasterZadania.class);
        startActivity(intent_master_zadania);
    }

    @Override
    public void onBackPressed() {
    }                 // Przycisk wstecz zablokowany

    public void new_project(View view) {
        Intent intent_new_project = new Intent(this, NowyProjekt.class);
        startActivity(intent_new_project);
    }       // Dodanie nowego projektu

    public void profil(View view) {
        Intent intent_profil = new Intent(this, Profil.class);
        startActivity(intent_profil);
    }           // Przechodzi do profilu

    public void elementy(){
        String active, name;
        int pro, time;
        data = new ArrayList<Elementy>();
        for(int i = 0; i< Dane.projects_list.size(); i++){
            if(Dane.projects_list.get(i).get("user_master_id").toString().equals(myID)){
                pro = 0; time = 0;
                for (int j=0; j<Dane.tasks_list.size(); j++){
                    if(Dane.tasks_list.get(j).get("project_id").toString().equals(Dane.projects_list.get(i).get("project_id").toString())){
                        pro += Integer.parseInt(Dane.tasks_list.get(j).get("used_time").toString());
                        time += Integer.parseInt(Dane.tasks_list.get(j).get("time").toString());
                    }
                }
                if(pro != 0){pro  = (pro*100/time);}
                if(Integer.parseInt(Dane.projects_list.get(i).get("time").toString())>pro){
                    active = "Aktywne";
                }
                else{active = "Nieaktywne";}
                name = Dane.projects_list.get(i).get("name").toString();
                data.add(new Elementy(pro, name, String.valueOf(pro) + "%", active));
            }
        }
        adapter = new ElementyProjektow(this, data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }                   // Pokazywanie projektów

    public void projekty(){
        projekty_lista = new ArrayList<String>();
        int i;
        for(i=0;i<Dane.projects_list.size();i++){
            if(Dane.projects_list.get(i).get("user_master_id").toString().equals(myID)){
                projekty_lista.add(Dane.projects_list.get(i).get("project_id").toString());
            }
        }
    }                   // Szukanie projektów
}
