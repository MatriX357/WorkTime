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

public class NewTask extends AppCompatActivity {
    private TextView user;
    private TextView priority;
    private EditText task;
    private EditText time;
    private EditText info;
    static String idP = "";
    private String userID;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowe_zadanie);
        user = findViewById(R.id.editText_z_user);
        priority = findViewById(R.id.editText_z_priority);
        task = findViewById(R.id.editText_z_task);
        time = findViewById(R.id.editText_z_time);
        info = findViewById(R.id.editText_z_info);
        data = ManagerApplication.data;
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
                userID = data.users_list.get(item).get("user_id").toString();
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
            String new_task_uuid = UUID.randomUUID().toString();
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
            task_map.put("user_id", userID);
            data.tasks_list.add(task_map);
            Toast.makeText(this, "Dodano nowe zadanie!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{Toast.makeText(this, "Wprowadź poprawne data_S!", Toast.LENGTH_SHORT).show();}
    }               // Dodaj zadanie

    private String[] users(){
        String[] userss = new String[data.users_list.size()];
        for(int i = 0; i< data.users_list.size(); i++){
            userss[i] = data.users_list.get(i).get("name").toString();
        }
        return userss;
    }           // Tworzy listę Userów
}