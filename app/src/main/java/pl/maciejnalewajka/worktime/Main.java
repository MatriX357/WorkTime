package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    static EditText text_login, text_haslo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_login = findViewById(R.id.editText_main_email);
        text_haslo = findViewById(R.id.editText_main_password);
    }

    public void logIn(View view) {


        // Sprawdzenie pól
        //login
        boolean l = false;
        //boolean h = false;
        int cyfry = 0;
        char[] znaki = text_login.getText().toString().toCharArray();
        for (char znak : znaki) {
            if (znak == '@') {
                l = true;
            }
        }
        //hasło
        char[] znaki2 = text_haslo.getText().toString().toCharArray();
        for(int i=0; i<text_haslo.getText().toString().length(); i++){
            char znak = znaki2[i];
            if(znak=='0' && znak=='9')
            {
                cyfry ++;
            }
        }
        //Sprawdzenie
        if(!l){
            Toast.makeText(this, "Podaj poprawny e-mail!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (text_haslo.getText().toString().length() < 8 && cyfry < 2) {
                Toast.makeText(this, "Podaj poprawne hasło!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent_zal = new Intent(this, Master.class);
                startActivity(intent_zal);
            }
        }
    }

    public void register(View view){
        Intent intent_re = new Intent(this, Rejestracja.class);
        startActivity(intent_re);
    }
}
