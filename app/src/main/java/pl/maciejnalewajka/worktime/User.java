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

public class User extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView lv;
    private SparseArray<String> projects_map = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        lv = findViewById(R.id.list_view_u);
    }

    @Override
    protected void onResume() {
        super.onResume();
        elements();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ManagerApplication.project_uuid = projects_map.get(position);
        Intent intent_user_zadania = new Intent(this, UserTasks.class);
        startActivity(intent_user_zadania);
    }

    @Override
    public void onBackPressed() {
    }                 // Przycisk wstecz zablokowany

    public void profile(View view) {
        Intent intent_profil = new Intent(this, Profil.class);
        startActivity(intent_profil);
    }       // Pzrycisk profile

    private void elements(){
        String active, name;
        int pro, used, amount;
        ArrayList<Elements> data_S = new ArrayList<>();
        try {
            int position = 0;
            for (String i : ManagerApplication.projects_list.keySet()) {
                projects_map.put(position, i);
                pro = 0;used = 0;amount = 0;
                for (String j : ManagerApplication.tasks_list.keySet()) {
                    System.out.println(j+i);
                    if(ManagerApplication.tasks_list.get(j).get("project_id").equals(i)){
                        pro += Integer.parseInt(ManagerApplication.tasks_list.get(j).get("time"));
                        used += Integer.parseInt(ManagerApplication.tasks_list.get(j).get("used_time"));
                        amount += 1;
                    }
                }
                if (used != 0) used = (used * 100) / pro;
                if (used < 100) active = "Aktywne";
                else active = "Nieaktywne";
                name = ManagerApplication.projects_list.get(i).get("name");
                data_S.add(new Elements(used, name, String.valueOf(amount), active));
                position = position + 1;

            }
        }catch (IndexOutOfBoundsException e ) {
            elements();
        }
        ArrayAdapter<Elements> adapter = new ProjectsElements(this, data_S);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }               // Wykresy i projects
}