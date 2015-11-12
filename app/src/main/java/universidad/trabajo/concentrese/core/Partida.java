package universidad.trabajo.concentrese.core;

import android.os.StrictMode;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by friendly on 06/10/2015.
 */

public class Partida {

    private int id;
    private Date fechaCreacion;
    private int totalSegundos;
    private int totalJugadas;
    private int jugadorId;


    private Jugador jugador;


    private String codeError;


    public Partida(int jugadorId, int total_segundos, int total_jugadas) {
        this.fechaCreacion = new Date();
        this.totalSegundos = total_segundos;
        this.totalJugadas = total_jugadas;
        this.jugadorId = jugadorId;
    }


    public boolean toSave() {


        //permiso para acceder a internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //decimos que valores son los que se van a enviar
        Form formRestApi = new Form();

        formRestApi.set("fecha_registro", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.fechaCreacion));
        formRestApi.set("total_segundos", Integer.toString(this.totalSegundos));
        formRestApi.set("total_jugadas", Integer.toString(this.totalJugadas));
        formRestApi.set("jugador_id", Integer.toString(this.jugadorId));

        //creamos un recurso para consumir el servicio correspondiente
        ClientResource partidaResource = new ClientResource("http://192.168.0.24/concentrese/partidas");


        // Write the response entity on the console
        try {

            Representation response = partidaResource.post(formRestApi, MediaType.APPLICATION_JSON);
            JSONObject tmpJson = new JsonRepresentation(response).getJsonObject();

            this.id = Integer.parseInt(tmpJson.getString("id"));

            return true;
        } catch (JSONException e) {

            System.out.println(e);

        } catch (ResourceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                JSONObject tmpJson = new JsonRepresentation(e.getResponse().getEntity()).getJsonObject();
                this.codeError = tmpJson.getString("CODE_ERROR");
            } catch (JSONException eTwo) {
                System.out.println(eTwo);
                this.codeError = "UNKNOWN";
            } catch (IOException eTwo) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Jugador getJugador(){
        return this.jugador;
    }

    public int getTotalJugadas(){
        return this.totalJugadas;
    }

    public int getTotalSegundos(){
        return this.totalSegundos;
    }

}
