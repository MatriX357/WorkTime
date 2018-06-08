package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ConcurrentModificationException;

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
        name.setText(ManagerApplication.users_list.get(user_id).get("name"));
        email.setText(ManagerApplication.users_list.get(user_id).get("email"));
        phone.setText(ManagerApplication.users_list.get(user_id).get("phone"));
        StringBuilder g = new StringBuilder();
        for(int i = 0; i< ManagerApplication.users_list.get(user_id).get("password").length(); i++){
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

    public void logout(View view) throws IOException {
        app.save_data();
        ManagerApplication.users_list_local=ManagerApplication.users_list;
        ManagerApplication.projects_list_local=ManagerApplication.projects_list;
        ManagerApplication.tasks_list_local=ManagerApplication.projects_list;
        app.downloadData();
        sendToServerUser();
        Intent intent_edit_log = new Intent(getApplicationContext(), Main.class);
        startActivity(intent_edit_log);
    }// Wylogowanie i usuwanie loginu i hasła z pamięci

    public void sendToServerUser() throws IOException {
        try {
            app.sendToServerUser();
            app.sendToServerProject();
            app.sendToServerTask();
        }catch (ConcurrentModificationException|NullPointerException e){
            sendToServerUser();
        }
    }
}