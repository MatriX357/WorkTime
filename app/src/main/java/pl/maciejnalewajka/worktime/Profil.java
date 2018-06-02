package pl.maciejnalewajka.worktime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Profil extends AppCompatActivity {
    TextView name;
    TextView email;
    private TextView password;
    TextView phone;
    private SharedPreferences.Editor editor;
    private static final String NAME = "name";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        name = findViewById(R.id.editText_p_name);
        email = findViewById(R.id.editText_p_email);
        password = findViewById(R.id.editText_p_password);
        phone = findViewById(R.id.editText_p_phone);
        SharedPreferences sharedPreferences = getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
        }

    @Override
    protected void onResume() {
        super.onResume();
        Data data = new Data();
        name.setText(data.getMy_hash().get("name").toString());
        email.setText(data.getMy_hash().get("email").toString());
        phone.setText(data.getMy_hash().get("phone").toString());
        StringBuilder g = new StringBuilder();
        for(int i = 0; i< data.getMy_hash().get("password").toString().length(); i++){
            g.append("*");}
        password.setText(g.toString());
    }

    public void back(View view) {
        finish();
    }   // Powrót do widoku projektów

    public void edit(View view) {
        Intent intent_edit_pro = new Intent(this, ProfilEdit.class);
        startActivity(intent_edit_pro);
    }           // Przejście do okna edycji

    public void logout(View view) {
        editor.remove(LOGIN);
        editor.remove(PASSWORD);
        editor.commit();
        Intent intent_edit_log = new Intent(getApplicationContext(), Main.class);
        startActivity(intent_edit_log);
    }       // Wylogowanie i usuwanie loginu i hasła z pamięci
}