package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

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
        UserZadania.id = projekty_lista.get(position);
        UserZadanie.idMaster = projekty_lista.get(position);
        Intent intent_user_zadania = new Intent(this, UserZadania.class);
        startActivity(intent_user_zadania);
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
        int pro, used, ilosc;
        data = new ArrayList<Elementy>();

        for(int i = 0; i< Dane.projects_list.size(); i++){
            for(int k=0;k<projekty_lista.size();k++){
                if(projekty_lista.get(k).equals(Dane.projects_list.get(i).get("project_id").toString())){
                    pro = 0; used = 0; ilosc = 0;
                    for (int j=0; j<Dane.tasks_list.size(); j++){
                        if(Dane.tasks_list.get(j).get("project_id").toString().equals(Dane.projects_list.get(i).get("project_id").toString()) &&
                                Dane.tasks_list.get(j).get("user_id").toString().equals(myID)){
                            pro += Integer.parseInt(Dane.tasks_list.get(j).get("time").toString());
                            used += Integer.parseInt(Dane.tasks_list.get(j).get("used_time").toString());
                            ilosc +=1;
                        }
                    }
                    if(used != 0){used = (used*100)/pro;}
                    if(used<100){
                        active = "Aktywne";
                    }
                    else{active = "Nieaktywne";}
                    name = Dane.projects_list.get(i).get("name").toString();
                    data.add(new Elementy(used, name, String.valueOf(ilosc), active));
                }
            }

        }
        adapter = new ElementyProjektow(this, data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }               // Wykresy i projekty

    public void projekty(){
        projekty_lista = new ArrayList<String>();
        int i;
        for(i=0;i<Dane.tasks_list.size();i++){
            if(Dane.tasks_list.get(i).get("user_id").toString().equals(myID)){
                if(!projekty_lista.contains(Dane.tasks_list.get(i).get("project_id"))){
                    projekty_lista.add(Dane.tasks_list.get(i).get("project_id").toString());
                }
            }
        }
    }               // Tworzy listę projektów
}