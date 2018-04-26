package pl.maciejnalewajka.worktime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void rejestracja(View view) {
        setContentView(R.layout.activity_rejestracja);
    }

    public void zaloguj(View view) {
        setContentView(R.layout.activity_master);
    }

    public void anuluj(View view) {
        setContentView(R.layout.activity_main);
    }

//    public void profil(View view) {
//        setContentView(R.layout.activity_profil);
//    }

    public void nowyprojekt(View view) {
        setContentView(R.layout.activity_nowy_projekt);
    }
}
