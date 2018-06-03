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
    private String myID;
    private static Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        lv = findViewById(R.id.list_view_u);
        data = ManagerApplication.data;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Data data = new Data();
        myID = data.getMy_hash().get("user_id").toString();
        projects();
        elementy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserTasks.id = projects_list.get(position);
        UserTask.idMaster = projects_list.get(position);
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

    private void elementy(){
        String active, name;
        int pro, used, amount;
        ArrayList<Elements> data_S = new ArrayList<>();

        for(int i = 0; i< data.projects_list.size(); i++){
            for(int k = 0; k< projects_list.size(); k++){
                if(projects_list.get(k).equals(data.projects_list.get(i).get("project_id").toString())){
                    pro = 0; used = 0; amount = 0;
                    for (int j = 0; j< data.tasks_list.size(); j++){
                        if(data.tasks_list.get(j).get("project_id").toString().equals(data.projects_list.get(i).get("project_id").toString()) &&
                                data.tasks_list.get(j).get("user_id").toString().equals(myID)){
                            pro += Integer.parseInt(data.tasks_list.get(j).get("time").toString());
                            used += Integer.parseInt(data.tasks_list.get(j).get("used_time").toString());
                            amount +=1;
                        }
                    }
                    if(used != 0){used = (used*100)/pro;}
                    if(used<100){
                        active = "Aktywne";
                    }
                    else{active = "Nieaktywne";}
                    name = data.projects_list.get(i).get("name").toString();
                    data_S.add(new Elements(used, name, String.valueOf(amount), active));
                }
            }

        }
        ArrayAdapter<Elements> adapter = new ProjectsElements(this, data_S);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }               // Wykresy i projects

    private void projects(){
        projects_list = new ArrayList<>();
        int i;
        for(i=0; i< data.tasks_list.size(); i++){
            if(data.tasks_list.get(i).get("user_id").toString().equals(myID)){
                if(!projects_list.contains(data.tasks_list.get(i).get("project_id")))
                    projects_list.add(data.tasks_list.get(i).get("project_id").toString());
            }
        }
    }               // Tworzy listę projektów
}