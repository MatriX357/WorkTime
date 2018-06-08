package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import pl.maciejnalewajka.worktime.Elements.Elements;
import pl.maciejnalewajka.worktime.Elements.ProjectsElements;

public class Master extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView lv;
    private SparseArray<String> projects_map = new SparseArray<>();
    private ManagerApplication app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        lv = findViewById(R.id.list_view_m);
        app = (ManagerApplication) getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        app.loadCheckData();
        elements();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(position+" "+id);
        ManagerApplication.project_uuid = projects_map.get(position);
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
            int position = 0;
            for (String i : ManagerApplication.projects_list.keySet()) {
                if (ManagerApplication.projects_list.get(i).get("user_master_id").equals(ManagerApplication.user_uuid)) {
                    projects_map.put(position, i);
                    pro = 0;
                    time = 0;
                    System.out.println(i + " p");
                    for (String j : ManagerApplication.tasks_list.keySet()) {
                        System.out.println(j + " " + i);
                        if (ManagerApplication.tasks_list.get(j).get("project_id").equals(i)) {
                            pro += Integer.parseInt(ManagerApplication.tasks_list.get(j).get("used_time"));
                            time += Integer.parseInt(ManagerApplication.tasks_list.get(j).get("time"));
                        }
                    }
                    if (pro != 0) {
                        pro = (pro * 100) / time;
                    }
                    if (pro < 100) {
                        active = "Aktywne";
                    } else {
                        active = "Nieaktywne";
                    }
                    name = ManagerApplication.projects_list.get(i).get("name");
                    data_S.add(new Elements(pro, name, String.valueOf(pro) + "%", active));
                    position = position + 1;
                    System.out.println(i + " k");
                }
            }
        }catch (IndexOutOfBoundsException e){
            elements();
        }
        ArrayAdapter<Elements> adapter = new ProjectsElements(this, data_S);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }                   // Pokazywanie projektów// Szukanie projektów
}
