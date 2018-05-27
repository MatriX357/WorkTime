package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.UUID;

public class Rejestracja extends AppCompatActivity {
    static EditText name, email, password, password2, phone;
    static String register_uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);
        name = findViewById(R.id.editText_r_name);
        email = findViewById(R.id.editText_r_email);
        password = findViewById(R.id.editText_r_password);
        password2 = findViewById(R.id.editText_r_password2);
        phone = findViewById(R.id.editText_r_phone);
    }

    public void register(View view) {
        boolean s = sprawdz();
        if (s == true) {
            register_uuid = UUID.randomUUID().toString();
            String nowe_haslo = revers();
            HashMap<String, Object> user_map = new HashMap<>();
            user_map.put("user_id", register_uuid);
            user_map.put("name", name.getText().toString());
            user_map.put("email", email.getText().toString());
            user_map.put("password", nowe_haslo);
            user_map.put("phone", phone.getText().toString());
            user_map.put("type", "User");
            user_map.put("company_id", "414dfebc-5bb1-11e8-9566-a01d48a8405c");
            Dane.projects_list.add(user_map);
            Toast.makeText(this, "Zarejestrowano!", Toast.LENGTH_SHORT).show();
            back(view);
        }
    }         // Rejestracja

    public void back(View view) {
        finish();
    }    // Przycisk wróć

    private boolean sprawdz() {
        boolean a = false;
        int cyfry = 0;
        //login
        char[] znaki = email.getText().toString().toCharArray();
        for (int i = 0; i < znaki.length; i++) {
            char znak = znaki[i];
            if (znak == '@') {
                a = true;
            }
        }
        //hasło
        char[] znaki2 = password.getText().toString().toCharArray();
        for (int i = 0; i < password.getText().toString().length(); i++) {
            char znak = znaki2[i];
            if (znak >= '0' && znak <= '9') {
                cyfry++;
            }
        }
        if (a == false) {
            Toast.makeText(this, "Podaj poprawny e-mail!", Toast.LENGTH_SHORT).show();
        } else {
            if (password.getText().toString().length() < 8 || cyfry < 2) {
                Toast.makeText(this, "Hasło musi zawierać 8 znaków w tym dwie cyfry!", Toast.LENGTH_SHORT).show();
            } else {
                if (!password.getText().toString().equals(password2.getText().toString())) {
                    Toast.makeText(this, "Podane hasła nie są takie same!", Toast.LENGTH_SHORT).show();
                } else {
                    if (phone.getText().toString().equals("")) {
                        Toast.makeText(this, "Podaj numer telefonu!", Toast.LENGTH_SHORT).show();
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }             // Sprawdza wpisane dane

    public String revers(){
        String nowy = new StringBuilder(password.getText().toString()).reverse().toString();
        return nowy;
    }               // Kodowanie hasła
}