package universidad.trabajo.concentrese.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import universidad.trabajo.concentrese.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.btn_iniciar_sesion:

                Intent intentIniciarSesion = new Intent(SignInActivity.this,LogInActivity.class);
                startActivity(intentIniciarSesion);

                break;
            case R.id.btn_registrarme:

                Intent intentRegistrarme = new Intent(SignInActivity.this,UserRegisterActivity.class);
                startActivity(intentRegistrarme);

                break;
        }

    }

}
