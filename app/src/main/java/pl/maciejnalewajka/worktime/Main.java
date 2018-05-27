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
    static EditText text_login, text_haslo;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String NAME = "name";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    Dane dane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_login = findViewById(R.id.editText_main_email);
        text_haslo = findViewById(R.id.editText_main_password);
        sharedPreferences = getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        restoreData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        PobieranieUserow pobierz_userow = new PobieranieUserow();
        pobierz_userow.execute();
        PobieranieProjektow pobierz_projekty = new PobieranieProjektow();
        pobierz_projekty.execute();
        PobieranieTaskow pobierz_taski = new PobieranieTaskow();
        pobierz_taski.execute();
        dane = new Dane();
    }

    public void logIn(View view) {


        if(spr() == true && search() == true){
            save_data();
            if(dane.getMy_hash().get("type").toString().equals("Master")){
                Intent intent_zaloguj = new Intent(this, Master.class);
                startActivity(intent_zaloguj);
            }
            else{
                Intent intent_zaloguj = new Intent(this, User.class);
                startActivity(intent_zaloguj);
            } }
        else{
        } }

    public void register(View view){
        Intent intent_re = new Intent(this, Rejestracja.class);
        startActivity(intent_re);
    }       // Przenosi do rejestracji

    private boolean search(){
        try {
            for (int i = 0; i < dane.users_list.size(); i++) {
                if (dane.users_list.get(i).get("email").equals(text_login.getText().toString())) {
                    if (dane.users_list.get(i).get("password").toString().equals(text_haslo.getText().toString())) {
                        dane.setMy_hash(dane.users_list.get(i));
                        return true;
                    } } }
            Toast.makeText(this, "Błędne dane!", Toast.LENGTH_SHORT).show();
            return false;
        }
        catch (Exception e){
            return false;
        } }               // Uzupełnia dane z logowania

    private boolean spr(){
        //login
        boolean l = false;
        //boolean h = false;
        int cyfry = 0;
        char[] znaki = text_login.getText().toString().toCharArray();
        for (char znak : znaki) {
            if (znak == '@') {
                l = true;
            } }
        //hasło
        char[] znaki2 = text_haslo.getText().toString().toCharArray();
        for(int i=0; i<text_haslo.getText().toString().length(); i++){
            char znak = znaki2[i];
            if(znak=='0' && znak=='9')
            {
                cyfry ++;
            }}
        //Sprawdzenie
        if(!l){
            Toast.makeText(this, "Podaj poprawny e-mail!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (text_haslo.getText().toString().length() < 8 && cyfry < 2) {
                Toast.makeText(this, "Podaj poprawne hasło!", Toast.LENGTH_SHORT).show();
            }
            else {
                return true;
            } }
        return false;
    }               // Sprawdza czy email i hasła są prawidłowe

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


    public void revers(){
        new StringBuilder("lolek").reverse().toString();
    }
}
