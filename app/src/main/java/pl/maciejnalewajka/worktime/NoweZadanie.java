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

public class NoweZadanie extends AppCompatActivity {
    static TextView user, priority;
    static EditText task, time, info;
    static String new_task_uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowe_zadanie);
        user = (TextView)findViewById(R.id.editText_z_user);
        priority = (TextView)findViewById(R.id.editText_z_priority);
        task = (EditText)findViewById(R.id.editText_z_task);
        time = (EditText)findViewById(R.id.editText_z_time);
        info = (EditText)findViewById(R.id.editText_z_info);
    }

    public void back(View view) {
        finish();
    }

    public void setUser(View view){
        final String[] users = {"Maciej Nalewajka", "Szymon Szysz", "Mateusz Brodziak"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Osoby");
        builder.setItems(users, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                user.setText(users[item]);
            }
        }).getContext().setTheme(R.style.AppTheme);
        builder.create();
        builder.show();
    }

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
    }

    public void add(View view){
        if(task.getText().toString() != "" && Integer.parseInt(time.getText().toString()) != 0){
            Toast.makeText(this, "Dodano nowe zadanie!", Toast.LENGTH_SHORT).show();
            new_task_uuid = UUID.randomUUID().toString();
        }
        else{Toast.makeText(this, "Wprowadź poprawne dane!", Toast.LENGTH_SHORT).show();}
    }
}