package universidad.trabajo.concentrese.main;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import java.io.IOException;
import java.util.ArrayList;
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

        ArrayList<Partida> resultados = this.cargarResultados();

        if (resultados == null) {
            Toast message = Toast.makeText(getApplicationContext(),"No fue posible cargar la informacion del servidor",Toast.LENGTH_LONG);
            message.show();
        } else {
            System.out.println("registros: " + resultados);
            PartidasAdapter listViewAdapter = new PartidasAdapter(this, R.layout.partida_list_item, resultados);
            listaResultados = (ListView) findViewById(R.id.lista_resultados);
            listaResultados.setAdapter(listViewAdapter);
        }

    }

    private ArrayList<Partida> cargarResultados() {
        //permiso para acceder a internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //creamos un recurso para consumir el servicio correspondiente
        ClientResource partidasResource = new ClientResource("http://192.168.0.24/concentrese/partidas");

        try {

            Representation response = partidasResource.get(MediaType.APPLICATION_JSON);
            JSONObject tmpJson = new JsonRepresentation(response).getJsonObject();
            int numeroRegistros = Integer.parseInt(tmpJson.getString("total_rows"));
            tmpJson.getJSONArray("data");
            JSONArray tmpJsonArray = tmpJson.getJSONArray("data");

            ArrayList<Partida> resultados = new ArrayList<>();

            for (int i = 0; i < numeroRegistros; i++) {
                JSONObject tmpJsonObject = tmpJsonArray.getJSONObject(i);

                Jugador tmpJugador = new Jugador(tmpJsonObject.getString("jugador_nombre"), tmpJsonObject.getString("jugador_correo"), null);
                Partida tmpPartida = new Partida(
                        Integer.parseInt(tmpJsonObject.getString("jugador_id")),
                        Integer.parseInt(tmpJsonObject.getString("total_segundos")),
                        Integer.parseInt(tmpJsonObject.getString("total_jugadas"))
                );

                tmpPartida.setId(Integer.parseInt(tmpJsonObject.getString("id")));
                tmpPartida.setJugador(tmpJugador);
                resultados.add(tmpPartida);
            }

            return resultados;

        } catch (JSONException e) {
            System.out.println("error en el json: " + e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            System.out.println("error en IOException: " + e);
        }

        return null;
    }

}
