package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProfilEdit extends AppCompatActivity {
    static EditText name, email, password, password2, phone;
    public Profil profil = new Profil();
    Dane dane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_edit);
        name = (EditText) findViewById(R.id.editText_pe_name);
        email = (EditText) findViewById(R.id.editText_pe_email);
        password = (EditText) findViewById(R.id.editText_pe_password);
        password2 = (EditText) findViewById(R.id.editText_pe_password2);
        phone = (EditText) findViewById(R.id.editText_pe_phone);
        name.setText(profil.name.getText().toString());
        email.setText(profil.email.getText().toString());
        phone.setText(profil.phone.getText().toString());
        dane = new Dane();
    }

    public void back(View view) {
        finish();
    }    // Przycisk wróć

    public void change(View view){
        boolean a = false;
        char[] znaki = email.getText().toString().toCharArray();
        for(int i=0; i<znaki.length; i++){
            char znak = znaki[i];
            if (znak == '@') {
                a = true;
            } }
        int cyfry = 0;
        char[] znaki2 = password.getText().toString().toCharArray();
        for(int i=0; i<password.getText().toString().length(); i++){
            char znak = znaki2[i];
            if(znak>='0' && znak<='9')
            {
                cyfry ++;
            } }
        if(a == false){Toast.makeText(this, "Podaj poprawny e-mail!", Toast.LENGTH_SHORT).show();}
        else{
            if(!password.getText().toString().equals("") || !password2.getText().toString().equals("")) {
                if((znaki2.length < 8 || cyfry < 2)) {
                    Toast.makeText(this, "Podaj poprawne hasło!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if((!password.getText().toString().equals(password2.getText().toString()))){
                        Toast.makeText(this, "Podane hasła nie są takie same!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String g = "";
                        for(int i=0; i<password.getText().toString().length(); i++){
                            g += "*";
                        }
                        dane.getMy_hash().remove("password");
                        dane.getMy_hash().put("password", password.getText().toString());
                        dane.getMy_hash().remove("name");
                        dane.getMy_hash().put("name", name.getText().toString());
                        dane.getMy_hash().remove("email");
                        dane.getMy_hash().put("email", email.getText().toString());
                        dane.getMy_hash().remove("phone");
                        dane.getMy_hash().put("phone", phone.getText().toString());
                        finish();
                    }
                }
            }
            else{
                dane.getMy_hash().remove("name");
                dane.getMy_hash().put("name", name.getText().toString());
                dane.getMy_hash().remove("email");
                dane.getMy_hash().put("email", email.getText().toString());
                dane.getMy_hash().remove("phone");
                dane.getMy_hash().put("phone", phone.getText().toString());
                finish();
            }
        }
    }           // Zmienia dane jeżeli poprawne
}