package universidad.trabajo.concentrese.core;

import android.content.Context;
import android.content.SharedPreferences;
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

/**
 * Created by friendly on 14/10/2015.
 */
public class Auth {

    private String id;
    private String dateExpire;
    private String token;
    private String userId;

    private String codeError;


    public Auth() {

    }

    public Auth(String id, String dateExpire, String token, String userId) {
        this.id = id;
        this.dateExpire = dateExpire;
        this.token = token;
        this.userId = userId;
    }

    public Boolean toAuthenticate(String correo, String clave) {

        //permiso para acceder a internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //decimos que valores son los que se van a enviar
        Form formRestApi = new Form();

        formRestApi.set("correo", correo);
        formRestApi.set("clave", clave);

        //creamos un recurso para consumir el servicio correspondiente
        ClientResource AuthResource = new ClientResource("http://52.24.40.174/api_concentrece/login");


        // Write the response entity on the console
        try {

            Representation response = AuthResource.post(formRestApi, MediaType.APPLICATION_JSON);
            JSONObject tmpJson = new JsonRepresentation(response).getJsonObject();

            this.id = tmpJson.getString("id");
            this.dateExpire = tmpJson.getString("dateExpire");
            this.token = tmpJson.getString("token");
            this.userId = tmpJson.getString("user_id");

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
        return codeError;
    }

    public void toSave(Context tmpContext){
        SharedPreferences preferenceUserAuth = tmpContext.getSharedPreferences("userAuth", 0); // 0 - for private mode
        SharedPreferences.Editor userAuth = preferenceUserAuth.edit();

        userAuth.putString("id",this.id);
        userAuth.putString("date_expire",this.dateExpire);
        userAuth.putString("token",this.token);
        userAuth.putString("user_id",this.userId);

        userAuth.commit();
    }

    public boolean toLoad(Context tmpContext){
        SharedPreferences preferenceUserAuth = tmpContext.getSharedPreferences("userAuth", 0); // 0 - for private mode

        this.id = preferenceUserAuth.getString("id",null);

        if(this.id != null){
            this.dateExpire = preferenceUserAuth.getString("date_expire",null);
            this.token = preferenceUserAuth.getString("token",null);
            this.userId = preferenceUserAuth.getString("user_id",null);
            return true;
        }

        return false;
    }

    public void logout (Context tmpContext){
        SharedPreferences preferenceUserAuth = tmpContext.getSharedPreferences("userAuth", 0); // 0 - for private mode
        SharedPreferences.Editor userAuth = preferenceUserAuth.edit();

        userAuth.clear();
        userAuth.commit();
    }

    public String getDateExpire() {
        return dateExpire;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }
}
