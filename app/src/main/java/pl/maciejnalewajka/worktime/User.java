package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class User extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView lv;
    ArrayList<String> projekty_lista;
    ArrayList<Elementy> data;
    ArrayAdapter<Elementy> adapter;
    Dane dane;
    String myID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        lv = (ListView) findViewById(R.id.listview_u);
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
    }

    @Override
    public void onBackPressed() {
    }                 // Przycisk wstecz zablokowany

    public void profil(View view) {
        Intent intent_profil = new Intent(this, Profil.class);
        startActivity(intent_profil);
    }       // Pzrycisk profil

    public void elementy(){
        String active, name;
        int pro, ilosc;
        data = new ArrayList<Elementy>();
        for(int i = 0; i< Dane.projects_list.size(); i++){
            for(int k=0;k<projekty_lista.size();k++){
                if(projekty_lista.get(k).equals(Dane.projects_list.get(i).get("project_id").toString())){
                    pro = 0; ilosc = 0;
                    for (int j=0; j<Dane.tasks_list.size(); j++){
                        if(Dane.tasks_list.get(j).get("project_id").toString().equals(Dane.projects_list.get(i).get("project_id").toString())){
                            pro += Integer.parseInt(Dane.tasks_list.get(j).get("used_time").toString());
                        }
                        if(Dane.tasks_list.get(j).get("project_id").toString().equals(Dane.projects_list.get(i).get("project_id").toString()) &&
                                Dane.tasks_list.get(j).get("user_id").toString().equals(myID)){
                            ilosc +=1;
                        }
                    }
                    if(Integer.parseInt(Dane.projects_list.get(i).get("time").toString())>pro){
                        active = "Aktywne";
                    }
                    else{active = "Nieaktywne";}
                    name = Dane.projects_list.get(i).get("name").toString();
                    data.add(new Elementy(pro, name, String.valueOf(ilosc), active));
                }
            }

        }
        adapter = new ElementyProjektow(this, data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    public void projekty(){
        projekty_lista = new ArrayList<String>();
        String projekt="";
        for(int i=0;i<Dane.tasks_list.size();i++){
            if(Dane.tasks_list.get(i).get("user_id").toString().equals(myID) && !Dane.tasks_list.get(i).get("project_id").toString().equals(projekt)){
                projekt = Dane.tasks_list.get(i).get("project_id").toString();
                projekty_lista.add(projekt);
            }
        }
    }
}