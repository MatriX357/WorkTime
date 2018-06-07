package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProfilEdit extends AppCompatActivity {
    private final Profil profil = new Profil();
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText password2;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_edit);
        name = findViewById(R.id.editText_pe_name);
        email = findViewById(R.id.editText_pe_email);
        password = findViewById(R.id.editText_pe_password);
        password2 = findViewById(R.id.editText_pe_password2);
        phone = findViewById(R.id.editText_pe_phone);
        name.setText(profil.name.getText().toString());
        email.setText(profil.email.getText().toString());
        phone.setText(profil.phone.getText().toString());
    }

    public void back(View view) {
        finish();
    }    // Przycisk wróć

    public void change(View view){
        boolean a = false;
        char[] marks = email.getText().toString().toCharArray();
        for (char mark : marks) {
            if (mark == '@') {
                a = true;
            }
        }
        int digits = 0;
        char[] marks2 = password.getText().toString().toCharArray();
        for(int i=0; i<password.getText().toString().length(); i++){
            char mark = marks2[i];
            if(mark>='0' && mark<='9')
            {
                digits ++;
            } }
        if(!a){Toast.makeText(this, "Podaj poprawny e-mail!", Toast.LENGTH_SHORT).show();}
        else{
            String user_id = ManagerApplication.user_uuid;
            if(!password.getText().toString().equals("") || !password2.getText().toString().equals("")) {
                if((marks2.length < 8 || digits < 2)) {
                    Toast.makeText(this, "Podaj poprawne hasło!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if((!password.getText().toString().equals(password2.getText().toString()))){
                        Toast.makeText(this, "Podane hasła nie są takie same!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        ManagerApplication.users_list.get(user_id).remove("password");
                        ManagerApplication.users_list.get(user_id).put("password", password.getText().toString());
                        ManagerApplication.users_list.get(user_id).remove("name");
                        ManagerApplication.users_list.get(user_id).put("name", name.getText().toString());
                        ManagerApplication.users_list.get(user_id).remove("email");
                        ManagerApplication.users_list.get(user_id).put("email", email.getText().toString());
                        ManagerApplication.users_list.get(user_id).remove("phone");
                        ManagerApplication.users_list.get(user_id).put("phone", phone.getText().toString());
                        finish();
                    }
                }
            }
            else{
                ManagerApplication.users_list.get(user_id).remove("name");
                ManagerApplication.users_list.get(user_id).put("name", name.getText().toString());
                ManagerApplication.users_list.get(user_id).remove("email");
                ManagerApplication.users_list.get(user_id).put("email", email.getText().toString());
                ManagerApplication.users_list.get(user_id).remove("phone");
                ManagerApplication.users_list.get(user_id).put("phone", phone.getText().toString());
                finish();
            }
        }
    }           // Zmienia data_S jeżeli poprawne
}