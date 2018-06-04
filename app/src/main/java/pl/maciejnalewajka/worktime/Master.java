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
    private Data data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        lv = findViewById(R.id.list_view_m);
        data = ManagerApplication.data;
        projects_list = ManagerApplication.project_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        try {
            for (int i = 0; i < data.projects_list.size(); i++) {
                pro = 0;
                time = 0;
                for (int j = 0; j < data.tasks_list.size(); j++) {
                    pro += Integer.parseInt(data.tasks_list.get(j).get("used_time").toString());
                    time += Integer.parseInt(data.tasks_list.get(j).get("time").toString());
                }
                if (pro != 0) {
                    pro = (pro * 100) / time;
                }
                if (pro < 100) {
                    active = "Aktywne";
                } else {
                    active = "Nieaktywne";
                }
                name = data.projects_list.get(i).get("name").toString();
                data_S.add(new Elements(pro, name, String.valueOf(pro) + "%", active));
            }
            ArrayAdapter<Elements> adapter = new ProjectsElements(this, data_S);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(this);
        }catch (IndexOutOfBoundsException e){
            elements();
        }
    }                   // Pokazywanie projektów// Szukanie projektów
}
