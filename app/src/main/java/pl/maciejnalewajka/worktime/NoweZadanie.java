package pl.maciejnalewajka.worktime;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class NoweZadanie extends AppCompatActivity {
    TextView user, priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowe_zadanie);
        user = (TextView)findViewById(R.id.editText_z_user);
        priority = (TextView)findViewById(R.id.editText_z_priority);
    }

    public void anuluj(View view) {
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
        final String[] prioritys = {"Brak", "Niski", "Normalny", "Wysoki"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Priorytet");
        builder.setItems(prioritys, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                priority.setText(prioritys[item]);
            }
        }).getContext().setTheme(R.style.AppTheme);
        builder.create();
        builder.show();
    }
}