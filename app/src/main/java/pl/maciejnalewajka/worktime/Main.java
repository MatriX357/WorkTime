package pl.maciejnalewajka.worktime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    private EditText text_login;
    private EditText text_haslo;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String NAME = "name";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_login = findViewById(R.id.editText_main_email);
        text_haslo = findViewById(R.id.editText_main_password);
        sharedPreferences = getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
        restoreData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        UsersDownload users_download = new UsersDownload();
        users_download.execute();
        ProjectsDownload projects_download = new ProjectsDownload();
        projects_download.execute();
        TasksDownload tasks_download = new TasksDownload();
        tasks_download.execute();
        data = new Data();
    }

    public void logIn(View view) {


        if(search()){
            save_data();
            if(data.getMy_hash().get("type").toString().equals("Master")){
                Intent intent_login = new Intent(this, Master.class);
                startActivity(intent_login);
            }
            else{
                Intent intent_login = new Intent(this, User.class);
                startActivity(intent_login);
            } }
    }         // Przycisk do logowania

    public void register(View view){
        Intent intent_re = new Intent(this, Registration.class);
        startActivity(intent_re);
    }       // Przenosi do rejestracji

    private boolean search(){

        try {
            for (int i = 0; i < Data.users_list.size(); i++) {
                if (Data.users_list.get(i).get("email").equals(text_login.getText().toString())) {
                    if (Data.users_list.get(i).get("password").toString().equals(revers())) {
                        data.setMy_hash(Data.users_list.get(i));
                        return true;
                    } } }
            Toast.makeText(this, "Błędne data!", Toast.LENGTH_SHORT).show();
            return false;
        }
        catch (Exception e){
            return false;
        } }               // Uzupełnia data z logowania

    private void restoreData() {
        String saved_l = sharedPreferences.getString(LOGIN, "");
        String saved_p = sharedPreferences.getString(PASSWORD, "");
        text_login.setText(saved_l);
        text_haslo.setText(saved_p);
    }           // Ustawia Login i hasło z pamięci

    private void save_data(){
        String text_l = text_login.getText().toString();
        String text_h = text_haslo.getText().toString();
        editor.putString(LOGIN, text_l);
        editor.putString(PASSWORD, text_h);
        editor.commit();
    }               // Zapisuje login i hasło w pamięci

    @Override
    public void onBackPressed() {
    }           // Wyłączenie przycisku wstecz

    private String revers(){
        return new StringBuilder(text_haslo.getText().toString()).reverse().toString();
    }               // Rozkodowanie hasła
}
