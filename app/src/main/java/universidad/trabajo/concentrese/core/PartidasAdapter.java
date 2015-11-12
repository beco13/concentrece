package universidad.trabajo.concentrese.core;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import universidad.trabajo.concentrese.R;



/**
 * Created by friendly on 07/10/2015.
 */
public class PartidasAdapter extends ArrayAdapter<Partida> {

    ArrayList<Partida> partidas;
    Context context;
    int layoutResourceId;

    public PartidasAdapter(Context context, int layoutResourceId, ArrayList<Partida> partidas) {
        super(context, layoutResourceId, partidas);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.partidas = partidas;
    }


    public View getView(int posicion, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflaterListItem = ((Activity) context).getLayoutInflater();
            row = inflaterListItem.inflate(this.layoutResourceId, parent, false);
        }

        //Partida tmpPartida = this.partidas.get(posicion);
        Partida tmpPartida = getItem(posicion);

        TextView nombreJugador = (TextView) row.findViewById(R.id.nombre_jugador);
        TextView totalJugadas = (TextView) row.findViewById(R.id.total_jugadas);
        TextView totalMinutos = (TextView) row.findViewById(R.id.total_minutos);


        nombreJugador.setText(tmpPartida.getJugador().getNombre());
        totalJugadas.setText(Integer.toString(tmpPartida.getTotalJugadas()) + " Jugada(s)");
        totalMinutos.setText(this.segundosFormatoTiempo(tmpPartida.getTotalSegundos()));

        return row;
    }

    private String segundosFormatoTiempo(int segundos){

        int minutos = 0;
        int tmpSegundos = 0;

        String tiempo = "00:00";

        for (int i = 0; i < segundos;i++){
            tiempo = this.toFormatMinutes(minutos) + ":" + this.toFormatSeconds(tmpSegundos);
            tmpSegundos++;
            if(tmpSegundos == 60){
                tmpSegundos = 0;
                minutos++;
            }
        }

        return tiempo;
    }


    private String toFormatSeconds(int seconds) {
        if (Integer.toString(seconds).length() == 1) {
            return "0" + String.valueOf(seconds);
        } else {
            return String.valueOf(seconds);
        }
    }

    private String toFormatMinutes(int minutes) {
        if (Integer.toString(minutes).length() == 1) {
            return "0" + String.valueOf(minutes);
        } else {
            return String.valueOf(minutes);
        }
    }

}
