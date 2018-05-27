package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserZadanie extends AppCompatActivity {
    static String task_id, idMaster;
    Button button;
    TextView name, time, priority, extraInfo, master;
    Dane dane;
    String myID;
    Boolean b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_start);
        name = (TextView) findViewById(R.id.textView_us_name);
        time = (TextView) findViewById(R.id.textView_us_time);
        priority = (TextView) findViewById(R.id.textView_us_priority);
        extraInfo = (TextView) findViewById(R.id.textView_us_extra_info);
        master = (TextView) findViewById(R.id.textView_us_master);
        button = (Button) findViewById(R.id.button_us);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dane = new Dane();
        myID = dane.getMy_hash().get("user_id").toString();
        task();
    }

    public void start(View view){
        stst();
    }

    public void back(View view){
        finish();
    }       // Przycisk wstecz

    public void task(){
        for(int i=0;i<Dane.tasks_list.size();i++){
            if(Dane.tasks_list.get(i).get("task_id").equals(task_id)) {
                name.setText("Nazwa: " + Dane.tasks_list.get(i).get("task").toString());
                time.setText("Czas: " + Dane.tasks_list.get(i).get("time").toString());
                priority.setText("Priorytet: " + Dane.tasks_list.get(i).get("priority").toString());
                extraInfo.setText("Info: " + Dane.tasks_list.get(i).get("extra_info").toString());
            }
        }
        for(int i=0;i<Dane.projects_list.size();i++){
            if(Dane.projects_list.get(i).get("project_id").equals(idMaster)){
                for(int j=0;j<Dane.users_list.size();j++){
                    if(Dane.users_list.get(j).get("user_id").equals(Dane.projects_list.get(i).get("user_master_id").toString())){
                        master.setText("Master: " + Dane.users_list.get(j).get("name").toString());
                    }
                }
            }
        }
    }                         // Uzupełnia dane

    public void stst(){
        if(button.getText().equals("Rozpocznij Zadanie")){
            b = true;
            button.setText("Zakończ Zadanie");
        }
        else{
            b = false;
            button.setText("Rozpocznij Zadanie");
        }
    }                       // Zmienia stan i napis przycisku
}
