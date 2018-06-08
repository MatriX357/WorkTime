package pl.maciejnalewajka.worktime;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class NewTask extends AppCompatActivity {
    private TextView user;
    private TextView priority;
    private EditText task;
    private EditText time;
    private EditText info;
    static String idP = "";
    private String userID;
    private ManagerApplication app;
    String[] user_id;
    String[] user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (ManagerApplication) getApplication();
        setContentView(R.layout.activity_nowe_zadanie);
        user = findViewById(R.id.editText_z_user);
        priority = findViewById(R.id.editText_z_priority);
        task = findViewById(R.id.editText_z_task);
        time = findViewById(R.id.editText_z_time);
        info = findViewById(R.id.editText_z_info);
        idP = ManagerApplication.project_uuid;
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
                userID = user_id[item];
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
            app.addTask(new_task_uuid,user.getText().toString(),task.getText().toString(),Integer.parseInt(time.getText().toString()),0,"22.10.19",priority.getText().toString(),info.getText().toString(),idP,userID);
            finish();
        }
        else{Toast.makeText(this, "Wprowadź poprawne data_S!", Toast.LENGTH_SHORT).show();}
    }               // Dodaj zadanie

    private String[] users(){
        user_id = new String[ManagerApplication.users_list.size()];
        user_name = new String[ManagerApplication.users_list.size()];
        int position = 0;
        for(String i:ManagerApplication.users_list.keySet()){
            user_id[position] = i;
            user_name[position] = ManagerApplication.users_list.get(i).get("name");
            position = position + 1;
        }
        return user_name;
    }           // Tworzy listę Userów
}