package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void singin(View view) {
        setContentView(R.layout.activity_singin);
    }

    public void login(View view) {
        setContentView(R.layout.activity_master);
    }
}
