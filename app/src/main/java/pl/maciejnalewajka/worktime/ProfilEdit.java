package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilEdit extends AppCompatActivity {
    static EditText name, email, password, password2, phone;
    public Profil profil = new Profil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_edit);
        name = (EditText) findViewById(R.id.editText_pe_name);
        email = (EditText) findViewById(R.id.editText_pe_email);
        password = (EditText) findViewById(R.id.editText_pe_password);
        password2 = (EditText) findViewById(R.id.editText_pe_password2);
        phone = (EditText) findViewById(R.id.editText_pe_phone);
        name.setText(profil.get_name());
        email.setText(profil.get_email());
        phone.setText(profil.get_phone());
    }

    public void anuluj(View view) {
        finish();
    }

    public void sprawdz() {
        profil.name.setText(name.getText().toString());
        profil.email.setText(email.getText().toString());
        profil.phone.setText(phone.getText().toString());
        finish();
    }

    public void zmień(View view){
        boolean a = false;
        char[] znaki = email.getText().toString().toCharArray();
        for(int i=0; i<znaki.length; i++){
            char znak = znaki[i];
            if (znak == '@') {
                a = true;
            }
        }
        int cyfry = 0;
        char[] znaki2 = password.getText().toString().toCharArray();
        for(int i=0; i<password.getText().toString().length(); i++){
            char znak = znaki2[i];
            if(znak>='0' && znak<='9')
            {
                cyfry ++;
            }
        }
        if(a == false){Toast.makeText(this, "Podaj poprawny e-mail!", Toast.LENGTH_SHORT).show();}
        else if(!password.getText().toString().equals("") || !password2.getText().toString().equals("")) {
            if((znaki2.length < 8 || cyfry < 2)) {
                Toast.makeText(this, "Podaj poprawne hasło!", Toast.LENGTH_SHORT).show();
            }
            else{
                if((!password.getText().toString().equals(password2.getText().toString()))){
                    Toast.makeText(this, "Podane hasła nie są takie same!", Toast.LENGTH_SHORT).show();
                }
                else{
                    profil.password.setText(pass());
                    sprawdz();
                }
            }
        }
        else{
            sprawdz();
        }
    }

    private String pass(){
        String g = "";
        for(int i=0; i<password.getText().toString().length(); i++){
            g += "*";
        }
        return g;
    }
}
