package universidad.trabajo.concentrese.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

import universidad.trabajo.concentrese.R;
import universidad.trabajo.concentrese.core.*;

public class SeeResultsActivity extends AppCompatActivity {

    ListView listaResultados;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_results);


        Partida juegosRealizados [] = new Partida [] {
                new Partida(new Date(),new Date(),new Date(),180,35,new Jugador("Jacinto Ramirez","jacinto.ramirez@gmail.com","1234_clave")),
                new Partida(new Date(),new Date(),new Date(),120,30,new Jugador("Juliana Henao","juli.henao@gmail.com","4321_clave")),
        };

        PartidasAdapter listViewAdapter = new PartidasAdapter(this, R.layout.partida_list_item, juegosRealizados);

        listaResultados = (ListView) findViewById(R.id.lista_resultados);

        listaResultados.setAdapter(listViewAdapter);

    }
}
