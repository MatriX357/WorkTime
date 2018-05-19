package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Profil extends AppCompatActivity {
    static TextView name, email, password, phone;
    public Dane dane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        name = (TextView)findViewById(R.id.editText_p_name);
        email = (TextView)findViewById(R.id.editText_p_email);
        password = (TextView)findViewById(R.id.editText_p_password);
        phone = (TextView)findViewById(R.id.editText_p_phone);
        dane = new Dane(null, null);
        name.setText(dane.getMy_hash().get("name").toString());
        email.setText(dane.getMy_hash().get("email").toString());
        phone.setText(dane.getMy_hash().get("phone").toString());
        String g = "";
        for(int i=0; i<dane.getMy_hash().get("password").toString().length(); i++){
            g += "*";}
        password.setText(g);
        }

    public String get_name(){
        return name.getText().toString();
    }

    public String get_email(){
        return email.getText().toString();
    }

    public CharSequence get_phone(){
        return phone.getText();
    }

    public void back(View view) {
        finish();
    }

    public void edit(View view) {
        Intent intent_edit_pro = new Intent(this, ProfilEdit.class);
        startActivity(intent_edit_pro);
    }

}
