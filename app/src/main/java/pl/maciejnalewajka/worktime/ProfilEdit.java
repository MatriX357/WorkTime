package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    public void zmień(View view) {
        profil.name.setText(name.getText().toString());
        profil.email.setText(email.getText().toString());
        profil.phone.setText(phone.getText().toString());
        finish();
    }
}
