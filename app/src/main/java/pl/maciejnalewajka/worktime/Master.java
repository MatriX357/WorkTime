package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import pl.maciejnalewajka.worktime.Elements.Elements;
import pl.maciejnalewajka.worktime.Elements.ProjectsElements;

public class Master extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView lv;
    private ArrayList<String> projects_list;
    private String myID;
    private Data data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        lv = findViewById(R.id.list_view_m);
        data = ManagerApplication.data;
    }

    @Override
    protected void onResume() {
        super.onResume();
        myID = data.getMy_hash().get("user_id").toString();
        projects();
        elements();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TasksMaster.id = projects_list.get(position);
        NewTask.idP = projects_list.get(position);
        PersonMaster.idP = projects_list.get(position);
        Intent intent_master_zadania = new Intent(this, TasksMaster.class);
        startActivity(intent_master_zadania);
    }

    @Override
    public void onBackPressed() {
    }                 // Przycisk wstecz zablokowany

    public void new_project(View view) {
        Intent intent_new_project = new Intent(this, NewProject.class);
        startActivity(intent_new_project);
    }       // Dodanie nowego projektu

    public void profile(View view) {
        Intent intent_profil = new Intent(this, Profil.class);
        startActivity(intent_profil);
    }           // Przechodzi do profilu

    private void elements(){
        String active, name;
        int pro, time;
        ArrayList<Elements> data_S = new ArrayList<>();
        for(int i = 0; i< data.projects_list.size(); i++){
            if(data.projects_list.get(i).get("user_master_id").toString().equals(myID)){
                pro = 0; time = 0;
                for (int j = 0; j< data.tasks_list.size(); j++){
                    if(data.tasks_list.get(j).get("project_id").toString().equals(data.projects_list.get(i).get("project_id").toString())){
                        pro += Integer.parseInt(data.tasks_list.get(j).get("used_time").toString());
                        time += Integer.parseInt(data.tasks_list.get(j).get("time").toString());
                    }
                }
                if(pro != 0){pro  = (pro*100/time);}
                if(Integer.parseInt(data.projects_list.get(i).get("time").toString())>pro){
                    active = "Aktywne";
                }
                else{active = "Nieaktywne";}
                name = data.projects_list.get(i).get("name").toString();
                data_S.add(new Elements(pro, name, String.valueOf(pro) + "%", active));
            }
        }
        ArrayAdapter<Elements> adapter = new ProjectsElements(this, data_S);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }                   // Pokazywanie projektów

    private void projects(){
        projects_list = new ArrayList<>();
        int i;
        for(i=0; i< data.projects_list.size(); i++){
            if(data.projects_list.get(i).get("user_master_id").toString().equals(myID)){
                projects_list.add(data.projects_list.get(i).get("project_id").toString());
            }
        }
    }                   // Szukanie projektów
}
