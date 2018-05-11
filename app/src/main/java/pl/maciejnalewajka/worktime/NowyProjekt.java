package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class NowyProjekt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowy_projekt);
    }

    public void anuluj(View view) {
        finish();
    }
}