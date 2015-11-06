package universidad.trabajo.concentrese.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import universidad.trabajo.concentrese.R;
import universidad.trabajo.concentrese.core.Auth;
import universidad.trabajo.concentrese.main.*;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void loggin (){
        EditText fieldEmail = (EditText) findViewById(R.id.input_field_email);
        EditText fieldPass = (EditText) findViewById(R.id.input_field_pass);

        Auth authUser = new Auth();

        if(authUser.toAuthenticate(fieldEmail.getText().toString(),fieldPass.getText().toString())){
            authUser.toSave(getApplicationContext());
            Intent intentHome = new Intent(LogInActivity.this,MainActivity.class);
            startActivity(intentHome);
        }else{
            String msg;
            if(authUser.getCodeError().equals("AUTH_FAIL") ){
                msg = "Correo o contreseña incorrecta";
            }else{
                msg = "Se ha presentado un error, intentelo nuevamente("+authUser.getCodeError()+")";
            }
            Toast message = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG);
            message.show();
        }
    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_iniciar_sesion:
                this.loggin();
                break;
        }
    }

}
