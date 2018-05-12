package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MasterZadania extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_zadania);
    }

    public void back(View view) {
        finish();
    }

    public void new_task(View view) {
        Intent intent_nowe_zadanie = new Intent(this, NoweZadanie.class);
        startActivity(intent_nowe_zadanie);
    }
}
