package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.UUID;

public class Registration extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText password2;
    private EditText phone;

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
        boolean s = check();
        if (s) {
            String register_uuid = UUID.randomUUID().toString();
            String nowe_haslo = revers();
            HashMap<String, Object> user_map = new HashMap<>();
            user_map.put("user_id", register_uuid);
            user_map.put("name", name.getText().toString());
            user_map.put("email", email.getText().toString());
            user_map.put("password", nowe_haslo);
            user_map.put("phone", phone.getText().toString());
            user_map.put("type", "User");
            user_map.put("company_id", "414dfebc-5bb1-11e8-9566-a01d48a8405c");
            Data.projects_list.add(user_map);
            Toast.makeText(this, "Zarejestrowano!", Toast.LENGTH_SHORT).show();
            back(view);
        }
    }         // Registration

    public void back(View view) {
        finish();
    }    // Przycisk wróć

    private boolean check() {
        boolean a = false;
        int digits = 0;
        //login
        char[] marks = email.getText().toString().toCharArray();
        for (char mark : marks) {
            if (mark == '@') {
                a = true;
            }
        }
        //hasło
        char[] marks2 = password.getText().toString().toCharArray();
        for (int i = 0; i < password.getText().toString().length(); i++) {
            char mark = marks2[i];
            if (mark >= '0' && mark <= '9') {
                digits++;
            }
        }
        if (!a) {
            Toast.makeText(this, "Podaj poprawny e-mail!", Toast.LENGTH_SHORT).show();
        } else {
            if (password.getText().toString().length() < 8 || digits < 2) {
                Toast.makeText(this, "Hasło musi zawierać 8 znaków w tym dwie digits!", Toast.LENGTH_SHORT).show();
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
    }             // Sprawdza wpisane data

    private String revers() {
        return new StringBuilder(password.getText().toString()).reverse().toString();
    }               // Kodowanie hasła

}