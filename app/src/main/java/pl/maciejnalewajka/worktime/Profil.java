package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Profil extends AppCompatActivity {
    TextView name;
    TextView email;
    private TextView password;
    TextView phone;
    private ManagerApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        name = findViewById(R.id.editText_p_name);
        email = findViewById(R.id.editText_p_email);
        password = findViewById(R.id.editText_p_password);
        phone = findViewById(R.id.editText_p_phone);
        app = (ManagerApplication) getApplication();
        }

    @Override
    protected void onResume() {
        super.onResume();
        String user_id = ManagerApplication.user_uuid;
        name.setText(ManagerApplication.users_list.get(user_id).get("name").toString());
        email.setText(ManagerApplication.users_list.get(user_id).get("email").toString());
        phone.setText(ManagerApplication.users_list.get(user_id).get("phone").toString());
        StringBuilder g = new StringBuilder();
        for(int i = 0; i< ManagerApplication.users_list.get(user_id).get("password").toString().length(); i++){
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
        app.save_data();
        Intent intent_edit_log = new Intent(getApplicationContext(), Main.class);
        startActivity(intent_edit_log);
    }       // Wylogowanie i usuwanie loginu i hasła z pamięci
}