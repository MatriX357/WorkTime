package pl.maciejnalewajka.worktime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Profil extends AppCompatActivity {
    static TextView name, email, password, phone;
    Dane dane;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String NAME = "name";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        name = (TextView)findViewById(R.id.editText_p_name);
        email = (TextView)findViewById(R.id.editText_p_email);
        password = (TextView)findViewById(R.id.editText_p_password);
        phone = (TextView)findViewById(R.id.editText_p_phone);
        sharedPreferences = getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        }

    @Override
    protected void onResume() {
        super.onResume();
        dane = new Dane();
        name.setText(dane.getMy_hash().get("name").toString());
        email.setText(dane.getMy_hash().get("email").toString());
        phone.setText(dane.getMy_hash().get("phone").toString());
        String g = "";
        for(int i=0; i<dane.getMy_hash().get("password").toString().length(); i++){
            g += "*";}
        password.setText(g);
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