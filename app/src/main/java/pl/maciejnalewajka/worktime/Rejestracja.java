package pl.maciejnalewajka.worktime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Queue;
import java.util.UUID;

public class Rejestracja extends AppCompatActivity {
    static EditText mail, mail2, haslo, haslo2, code;
    static String register_uuid;
    public String url_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);
        mail = findViewById(R.id.editText_r_email);
        mail2 = findViewById(R.id.editText_r_email2);
        haslo = findViewById(R.id.editText_r_password);
        haslo2 = findViewById(R.id.editText_r_password2);
        code = findViewById(R.id.editText_r_code);
    }

    public void register(View view) throws IOException {
        boolean s = sprawdz();
        if(s == true){
            register_uuid = UUID.randomUUID().toString();
            if(code.getText().toString().equals("0")){
                // Konto master

            }
            else if(code.getText().toString() == "1"){
                // Konteo user
            }
            else{
                Toast.makeText(this, "Wpisz poprawny kod!", Toast.LENGTH_SHORT).show();
            }
            back(view);
        }
    }

    public void back(View view) {
        finish();
    }



    private boolean sprawdz(){
        boolean a=false;
        int cyfry = 0;

        //login
        char[] znaki = mail.getText().toString().toCharArray();
        for(int i=0; i<znaki.length; i++){
            char znak = znaki[i];
            if (znak == '@') {
                a = true;
            }
        }
        //hasło
        char[] znaki2 = haslo.getText().toString().toCharArray();
        for(int i=0; i<haslo.getText().toString().length(); i++){
            char znak = znaki2[i];
            if(znak>='0' && znak<='9')
            {
                cyfry ++;
            }
        }


        if(a == false){ Toast.makeText(this, "Podaj poprawny e-mail!", Toast.LENGTH_SHORT).show(); }
        else{
            if(!mail.getText().toString().equals(mail2.getText().toString())){
                Toast.makeText(this, "Podane e-maile nie są takie same!", Toast.LENGTH_SHORT).show(); }
            else{
                if (haslo.getText().toString().length() < 8 || cyfry < 2) {
                    Toast.makeText(this, "Hasło musi zawierać 8 znaków w tym dwie cyfry!", Toast.LENGTH_SHORT).show(); }
                else{
                    if(!haslo.getText().toString().equals(haslo2.getText().toString())){
                        Toast.makeText(this, "Podane hasła nie są takie same!", Toast.LENGTH_SHORT).show();
                    }
                    else{return true;}
                }
            }
        }
        return false;

    }

}
