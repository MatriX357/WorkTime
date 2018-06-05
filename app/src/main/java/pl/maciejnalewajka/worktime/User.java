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

public class User extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView lv;
    private ArrayList<String> projects_list;
    private static Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        lv = findViewById(R.id.list_view_u);
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
        UserTasks.id = projects_list.get(position);
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
            for (int k = 0; k < projects_list.size(); k++) {
                pro = 0;
                used = 0;
                amount = 0;
                for (int j = 0; j < data.tasks_list.size(); j++) {
                    if(data.tasks_list.get(j).get("project_id").toString().equals(data.projects_list.get(k).get("project_id").toString())){
                        pro += Integer.parseInt(data.tasks_list.get(j).get("time").toString());
                        used += Integer.parseInt(data.tasks_list.get(j).get("used_time").toString());
                        amount += 1;
                    }
                }
                if (used != 0) used = (used * 100) / pro;
                if (used < 100) active = "Aktywne";
                else active = "Nieaktywne";
                name = data.projects_list.get(k).get("name").toString();
                data_S.add(new Elements(used, name, String.valueOf(amount), active));

            }
        }catch (IndexOutOfBoundsException e ) {
            elements();
        }
        ArrayAdapter<Elements> adapter = new ProjectsElements(this, data_S);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }               // Wykresy i projects
}