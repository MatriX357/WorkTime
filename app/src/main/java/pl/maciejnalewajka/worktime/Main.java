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
    private Data data;
    private ManagerApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_login = findViewById(R.id.editText_main_name);
        text_haslo = findViewById(R.id.editText_main_password);
        text_login.setText(ManagerApplication.user_name);
        text_haslo.setText(ManagerApplication.password);
        app = (ManagerApplication) getApplication();


    }

    @Override
    protected void onResume() {
        super.onResume();
        data = ManagerApplication.data;
    }

    public void logIn(View view) {
        ManagerApplication.user_name = text_login.getText().toString();
        app.downloadData();
        if (ManagerApplication.ES){
            if(checkPassword()){
                app.save_data();
                if(ManagerApplication.user_type.equals("Master")){
                    Intent intent_login = new Intent(this, Master.class);
                    startActivity(intent_login);
                }
                else if(ManagerApplication.user_type.equals("User")){
                    Intent intent_login = new Intent(this, User.class);
                    startActivity(intent_login);
                } } }
        else{
            Toast.makeText(this, "Nie ma takiego użytkownika!", Toast.LENGTH_SHORT).show();
        }
    }         // Przycisk do logowania

    public void register(View view){
        Intent intent_re = new Intent(this, Registration.class);
        startActivity(intent_re);
    }       // Przenosi do rejestracji

    private boolean checkPassword(){

        try {
            if (ManagerApplication.password.equals(text_haslo.getText().toString())) {
                data.setMy_hash(data.users_list.get(0));
                return true; }
            Toast.makeText(this, "Błędne dane!", Toast.LENGTH_SHORT).show();
            return false;
        }
        catch (Exception e){
            return false;
        } }               // Uzupełnia data_S z logowania


    @Override
    public void onBackPressed() {
    }           // Wyłączenie przycisku wstecz
}
