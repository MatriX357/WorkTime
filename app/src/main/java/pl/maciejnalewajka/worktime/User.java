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
    private ListView lv;
    private ArrayList<String> projects_list;
    private String myID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        lv = findViewById(R.id.list_view_u);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Data data = new Data();
        myID = data.getMy_hash().get("user_id").toString();
        projekty();
        elementy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserZadania.id = projects_list.get(position);
        UserZadanie.idMaster = projects_list.get(position);
        Intent intent_user_zadania = new Intent(this, UserZadania.class);
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
        ArrayList<Elements> data = new ArrayList<>();

        for(int i = 0; i< Data.projects_list.size(); i++){
            for(int k = 0; k< projects_list.size(); k++){
                if(projects_list.get(k).equals(Data.projects_list.get(i).get("project_id").toString())){
                    pro = 0; used = 0; amount = 0;
                    for (int j = 0; j< Data.tasks_list.size(); j++){
                        if(Data.tasks_list.get(j).get("project_id").toString().equals(Data.projects_list.get(i).get("project_id").toString()) &&
                                Data.tasks_list.get(j).get("user_id").toString().equals(myID)){
                            pro += Integer.parseInt(Data.tasks_list.get(j).get("time").toString());
                            used += Integer.parseInt(Data.tasks_list.get(j).get("used_time").toString());
                            amount +=1;
                        }
                    }
                    if(used != 0){used = (used*100)/pro;}
                    if(used<100){
                        active = "Aktywne";
                    }
                    else{active = "Nieaktywne";}
                    name = Data.projects_list.get(i).get("name").toString();
                    data.add(new Elements(used, name, String.valueOf(amount), active));
                }
            }

        }
        ArrayAdapter<Elements> adapter = new ProjectsElements(this, data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }               // Wykresy i projekty

    private void projekty(){
        projects_list = new ArrayList<>();
        int i;
        for(i=0; i< Data.tasks_list.size(); i++){
            if(Data.tasks_list.get(i).get("user_id").toString().equals(myID)){
                if(!projects_list.contains(Data.tasks_list.get(i).get("project_id")))
                    projects_list.add(Data.tasks_list.get(i).get("project_id").toString());
            }
        }
    }               // Tworzy listę projektów
}