package pl.maciejnalewajka.worktime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    EditText text_login;
    EditText text_haslo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_login = findViewById(R.id.editText6);
        text_haslo = findViewById(R.id.editText5);
    }

    public void rejestracja(View view) {
        setContentView(R.layout.activity_rejestracja);
    }

    public void zaloguj(View view) {


        // Sprawdzenie pól
        //login
        boolean l = false;
        int cyfry = 0;
        char[] znaki = text_login.getText().toString().toCharArray();
        for(int i=0; i<znaki.length; i++){
            char znak = znaki[i];
            if(znak == '@'){l = true;}
        }
        //hasło
        char[] znaki2 = text_haslo.getText().toString().toCharArray();
        for(int i=0; i<znaki2.length; i++){
            char znak = znaki[i];
            if(znak>='0' && znak<='9')
            {
                cyfry ++;
            }
        }
        //Sprawdzenie
        if(l == false){
            Toast.makeText(this, "Podaj poprawny e-mail!", Toast.LENGTH_SHORT).show();
        }
        else{
            if(text_haslo.getText().toString().length() < 8 && cyfry < 2){
                Toast.makeText(this, "Podaj poprawne hasło!", Toast.LENGTH_SHORT).show();
            }
            else{setContentView(R.layout.activity_master);}}
    }

    public void anuluj(View view) {
        setContentView(R.layout.activity_main);
    }

//    public void profil(View view) {
//        setContentView(R.layout.activity_profil);
//    }

    public void nowyprojekt(View view) {
        setContentView(R.layout.activity_nowy_projekt);
    }
}
