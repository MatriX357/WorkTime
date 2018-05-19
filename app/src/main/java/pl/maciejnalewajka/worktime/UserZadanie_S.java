package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class UserZadanie_S extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_zadanie_s);
    }

    public void back(View view) {
        finish();
    }
}
