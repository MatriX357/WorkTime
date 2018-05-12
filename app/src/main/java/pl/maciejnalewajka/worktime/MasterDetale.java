package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MasterDetale extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detale);
    }


    public void anuluj(View view) {
        finish();
    }

    public void nowe_zadanie(View view) {
        Intent intent_nowezad = new Intent(this, NoweZadanie.class);
        startActivity(intent_nowezad);
    }
}
