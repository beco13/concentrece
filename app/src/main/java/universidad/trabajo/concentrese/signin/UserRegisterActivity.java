package universidad.trabajo.concentrese.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import universidad.trabajo.concentrese.R;
import universidad.trabajo.concentrese.core.Auth;
import universidad.trabajo.concentrese.core.Jugador;
import universidad.trabajo.concentrese.main.MainActivity;

public class UserRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_registrarme:
                    this.checkMe();
                break;
        }
    }

    private void checkMe (){
        EditText fieldName = (EditText) findViewById(R.id.input_field_name);
        EditText fieldEmail = (EditText) findViewById(R.id.input_field_email);
        EditText fieldPass = (EditText) findViewById(R.id.input_field_pass);

        if(this.toValidate(fieldName.getText().toString(),fieldEmail.getText().toString(),fieldPass.getText().toString())){
            Jugador nuevoJugador = new Jugador(fieldName.getText().toString(),fieldEmail.getText().toString(),fieldPass.getText().toString());
            if(nuevoJugador.toSave()){
                Auth authUser = new Auth();
                if(authUser.toAuthenticate(fieldEmail.getText().toString(),fieldPass.getText().toString())){
                    authUser.toSave(getApplicationContext());
                    Intent intentHome = new Intent(UserRegisterActivity.this,MainActivity.class);
                    startActivity(intentHome);
                }else{
                    Toast message = Toast.makeText(getApplicationContext(),"Te hemos registrado, pero hubo problema al continuar..",Toast.LENGTH_LONG);
                    message.show();
                }
            }else{
                String msg;
                switch (nuevoJugador.getCodeError()){
                    case "USER_REGISTER":
                        msg = "Verifique los datos e intentelo nuevamente.";
                        break;
                    case "USER_EXIST":
                        msg = "El usuario ya se encuentra registrado.";
                        break;
                    case "USER_REGISTER_LOGIN":
                        msg = "Se ha presentado un error.";
                        break;
                    default:
                        msg = "Error desconocido";
                        break;
                }
                Toast message = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG);
                message.show();
            }
        }
    }

    private Boolean toValidate(String name,String email, String pass){

        Boolean status = true;

        String msg = "";

        if(name.trim() == "" || name.trim().length() == 0 ){
            msg += " El campo nombre es obligatorio";
            status = false;
        }

        if(email.trim() == "" || email.trim().length() == 0){
            if(status == false){
                msg += "\n";
            }
            msg += "El campo correo es obligatorio";
            status = false;
        }

        if(email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$") == false){
            if(status == false){
                msg += "\n";
            }
            msg += "El campo correo debe tener un formato válido";
            status = false;
        }

        if(pass.trim() == "" || pass.trim().length() == 0 ){
            if(status == false){
                msg += "\n";
            }
            msg += "El campo contraseña es obligatorio";
            status = false;
        }

        if(status == false){
            Toast message = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG);
            message.show();
        }


        return status;
    }
}
