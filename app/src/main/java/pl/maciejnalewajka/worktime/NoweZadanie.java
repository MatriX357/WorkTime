package pl.maciejnalewajka.worktime;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.UUID;

public class NoweZadanie extends AppCompatActivity {
    String[] userss;
    static TextView user, priority;
    static EditText task, time, info;
    static String new_task_uuid;
    static String idP = "";
    String userid;
    Dane dane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowe_zadanie);
        user = (TextView)findViewById(R.id.editText_z_user);
        priority = (TextView)findViewById(R.id.editText_z_priority);
        task = (EditText)findViewById(R.id.editText_z_task);
        time = (EditText)findViewById(R.id.editText_z_time);
        info = (EditText)findViewById(R.id.editText_z_info);
        users();
    }

    public void back(View view) {
        finish();
    }       // przycisk wstecz

    public void setUser(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Osoby");
        builder.setItems(users(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                user.setText(users()[item]);
                userid = Dane.users_list.get(item).get("user_id").toString();
            }
        }).getContext().setTheme(R.style.AppTheme);
        builder.create();
        builder.show();
    }               // Ustawia usera

    public void setPriority(View view){
        final String[] priorities = {"Brak", "Niski", "Normalny", "Wysoki"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Priorytet");
        builder.setItems(priorities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                priority.setText(priorities[item]);
            }
        }).getContext().setTheme(R.style.AppTheme);
        builder.create();
        builder.show();
    }        // Ustawia priority

    public void add(View view){
        if(!task.getText().toString().equals("") && !time.getText().toString().equals("") &&
                !user.getText().toString().equals("") && !priority.getText().toString().equals("")){
            new_task_uuid = UUID.randomUUID().toString();
            HashMap<String, Object> task_map = new HashMap<>();
            task_map.put("task_id", new_task_uuid);
            task_map.put("name", user.getText().toString());
            task_map.put("task", task.getText().toString());
            task_map.put("time", time.getText().toString());
            task_map.put("used_time", "0");
            task_map.put("task_date", "22.10.19");
            task_map.put("priority", priority.getText().toString());
            task_map.put("extra_info", info.getText().toString());
            task_map.put("project_id", idP);
            task_map.put("user_id", userid);
            Dane.tasks_list.add(task_map);
            Toast.makeText(this, "Dodano nowe zadanie!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{Toast.makeText(this, "Wprowadź poprawne dane!", Toast.LENGTH_SHORT).show();}
    }               // Dodaj zadanie

    public final String[] users(){
        userss = new String[Dane.users_list.size()];
        for(int i=0;i<Dane.users_list.size();i++){
            userss[i] = Dane.users_list.get(i).get("name").toString();
        }
        return userss;
    }           // Tworzy listę Userów
}