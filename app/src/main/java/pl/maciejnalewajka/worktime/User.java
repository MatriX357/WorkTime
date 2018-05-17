package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class User extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
//        ListAdapter adapter = new SimpleAdapter(this, contactList,
//                R.layout.list_projects, new String[]{ "name","priority", "procent"},
//                new int[]{R.id.l_nazwa, R.id.l_priorytet, R.id.l_procent,});
//        lv.setAdapter(adapter);
    }

    public void profil(View view) {
        Intent intent_profil = new Intent(this, Profil.class);
        startActivity(intent_profil);
    }
}
