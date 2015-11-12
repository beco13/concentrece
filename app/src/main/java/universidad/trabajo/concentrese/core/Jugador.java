package universidad.trabajo.concentrese.core;


import android.os.StrictMode;
import android.text.BoringLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.util.*;

/**
 * Created by friendly on 06/10/2015.
 */
public class Jugador {

    private int id = 0;
    private String nombre;
    private String correo;
    private String clave;

    private String codeError;

    private Auth credentials;

    public Jugador(int id) {
        this.id = id;
    }

    public Jugador(String nombre, String correo, String clave) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean toSave() {
        if (this.id == 0) {
            return this.register();
        } else {
            return this.update();
        }
    }

    private Boolean register() {

        //permiso para acceder a internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //decimos que valores son los que se van a enviar
        Form formRestApi = new Form();

        formRestApi.set("nombre", this.nombre);
        formRestApi.set("correo", this.correo);
        formRestApi.set("clave", this.clave);

        ClientResource jugadorResource = new ClientResource("http://52.24.40.174/api_concentrece/jugadores");

        // Write the response entity on the console
        try {
            Representation response = jugadorResource.post(formRestApi, MediaType.APPLICATION_JSON);
            JSONObject tmpJson = new JsonRepresentation(response).getJsonObject();
            this.id = Integer.parseInt(tmpJson.getString("user_id"));
            return true;
        } catch (JSONException e) {
            System.out.println("error json:" +e);
            this.codeError = "UNKNOWN";
        } catch (ResourceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            System.out.println("error conexion:" + e);

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
            System.out.println("error ioexcepcion:" + e);
            this.codeError = "UNKNOWN";
        }
        return false;
    }

    private Boolean update() {

        //permiso para acceder a internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //decimos que valores son los que se van a enviar
        Form formRestApi = new Form();

        formRestApi.set("nombre", this.nombre);
        formRestApi.set("correo", this.correo);
        formRestApi.set("clave", this.clave);

        ClientResource jugadorResource = new ClientResource("http://52.24.40.174/api_concentrece/jugadores");

        // Write the response entity on the console
        try {

            Representation response = jugadorResource.put(formRestApi, MediaType.APPLICATION_JSON);
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

    public String getCodeError() {
        return this.codeError;
    }

}
