package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserZadanie extends AppCompatActivity {
    static String task_id, idMaster;
    private Button button;
    private TextView name;
    private TextView time;
    private TextView priority;
    private TextView extraInfo;
    private TextView master;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_start);
        name = findViewById(R.id.textView_us_name);
        time = findViewById(R.id.textView_us_time);
        priority = findViewById(R.id.textView_us_priority);
        extraInfo = findViewById(R.id.textView_us_extra_info);
        master = findViewById(R.id.textView_us_master);
        button = findViewById(R.id.button_us);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Data data = new Data();
        String myID = data.getMy_hash().get("user_id").toString();
        task();
    }

    public void start(View view){
        buttonCondition();
    }

    public void back(View view){
        finish();
    }       // Przycisk wstecz

    private void task(){
        for(int i = 0; i< Data.tasks_list.size(); i++){
            if(Data.tasks_list.get(i).get("task_id").equals(task_id)) {
                name.setText(String.format("Nazwa: %s", Data.tasks_list.get(i).get("task").toString()));
                time.setText(String.format("Czas: %s", Data.tasks_list.get(i).get("time").toString()));
                priority.setText(String.format("Priorytet: %s", Data.tasks_list.get(i).get("priority").toString()));
                extraInfo.setText(String.format("Info: %s", Data.tasks_list.get(i).get("extra_info").toString()));
            }
        }
        for(int i = 0; i< Data.projects_list.size(); i++){
            if(Data.projects_list.get(i).get("project_id").equals(idMaster)){
                for(int j = 0; j< Data.users_list.size(); j++){
                    if(Data.users_list.get(j).get("user_id").equals(Data.projects_list.get(i).get("user_master_id").toString())){
                        master.setText(String.format("Master: %s", Data.users_list.get(j).get("name").toString()));
                    }
                }
            }
        }
    }                         // Uzupełnia data

    private void buttonCondition(){
        if(button.getText().equals("Rozpocznij Zadanie")){
            button.setText(getString(R.string.finish_the_task));
        }
        else{
            button.setText(getString(R.string.start_the_task));
        }
    }                       // Zmienia stan i napis przycisku
}
