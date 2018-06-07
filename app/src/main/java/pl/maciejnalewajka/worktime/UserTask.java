package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pl.maciejnalewajka.worktime.Stopwatch.Stopwatch;

import static java.lang.String.format;
import static pl.maciejnalewajka.worktime.Stopwatch.Stopwatch.*;

public class UserTask extends AppCompatActivity {

    static String task_id = ManagerApplication.idT;
    private Button button;
    private TextView name;
    private TextView time;
    private TextView priority;
    private TextView extraInfo;
    private TextView master;
    private TextView stopwatch;
    private ManagerApplication app;


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
        stopwatch = findViewById(R.id.stopwatch);
        app = (ManagerApplication) getApplication();
        if (isStarted(task_id)){
            button.setText(getString(R.string.finish_the_task));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        app.save_data();
        task();
    }

    public void start(View view){
        buttonCondition();
    }

    public void back(View view){
        finish();
    }       // Przycisk wstecz

    private void task(){
        name.setText(format("Nazwa: %s", ManagerApplication.tasks_list.get(task_id).get("task").toString()));
        time.setText(format("Czas: %s", ManagerApplication.tasks_list.get(task_id).get("time").toString()));
        priority.setText(format("Priorytet: %s", ManagerApplication.tasks_list.get(task_id).get("priority").toString()));
        extraInfo.setText(format("Info: %s", ManagerApplication.tasks_list.get(task_id).get("extra_info").toString()));
        String project_id = (String) ManagerApplication.tasks_list.get(task_id).get("project_id");
        String user_master_id = (String) ManagerApplication.projects_list.get(project_id).get("user_master_id");
        master.setText(format("Master: %s", ManagerApplication.users_list.get(user_master_id).get("name").toString()));
    }                         // Uzupełnia data_S

    private void buttonCondition(){
        if (button.getText().equals(getString(R.string.start_the_task))) {
            if (isStarted())
            {
                Toast.makeText(this, "Inne zadanie jest rozpoczęte", Toast.LENGTH_SHORT).show();
            }else {
                button.setText(getString(R.string.finish_the_task));
                Stopwatch.start(task_id);
            }
        } else if (button.getText().equals(getString(R.string.finish_the_task))){
            button.setText(getString(R.string.start_the_task));
            Stopwatch.stop(task_id);
            stopwatch.setText(String.valueOf(getTime()));
        }
    }                       // Zmienia stan i napis przycisku
}
