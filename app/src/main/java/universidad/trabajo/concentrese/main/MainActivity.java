package universidad.trabajo.concentrese.main;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



import universidad.trabajo.concentrese.R;
import universidad.trabajo.concentrese.core.Auth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){

        switch (view.getId()){

            case R.id.btn_copyright:

                Intent intentCopyrightActivity = new Intent(MainActivity.this, CopyrightActivity.class);
                startActivity(intentCopyrightActivity);

                break;

            case R.id.btn_ver_resultados:

                Intent intentSeeResultsActivity = new Intent(MainActivity.this, SeeResultsActivity.class);
                startActivity(intentSeeResultsActivity);

                break;

            case R.id.btn_jugar:

                    Intent intentPlayActivity = new Intent(MainActivity.this, PlayActivity.class);
                    startActivity(intentPlayActivity);

                break;
            default:

                System.out.print("id back button: "+view.getId());

                break;
        }

    }

    @Override
    public void onBackPressed(){

        //super.onBackPressed();


        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder
                .setMessage("Desea cerrar sesión?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!

                        Auth userAuth = new Auth();
                        userAuth.logout(getApplicationContext());

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog tmpDialog = builder.create();
        tmpDialog.show();
    }


}
