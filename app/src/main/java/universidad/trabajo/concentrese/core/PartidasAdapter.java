package universidad.trabajo.concentrese.core;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import universidad.trabajo.concentrese.R;

/**
 * Created by friendly on 07/10/2015.
 */
public class PartidasAdapter extends ArrayAdapter<Partida> {

    Context context;
    int layoutResourceId;

    public PartidasAdapter(Context context, int layoutResourceId,  Partida [] partidas) {

        super(context, layoutResourceId, partidas);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }


    public View getView(int posicion, View convertView, ViewGroup parent){
        View row = convertView;

        if(row == null){
            LayoutInflater inflaterListItem = ((Activity)context).getLayoutInflater();
            row = inflaterListItem.inflate(this.layoutResourceId,parent,false);
        }

        Partida partida = getItem(posicion);

        TextView nombreJugador = (TextView) row.findViewById(R.id.nombre_jugador);
        TextView totalJugadas = (TextView) row.findViewById(R.id.total_jugadas);
        TextView totalMinutos = (TextView) row.findViewById(R.id.total_minutos);

        /*
        nombreJugador.setText(partida.getJugador().getNombre());
        totalJugadas.setText(Integer.toString(partida.getTotal_jugadas()) + " Jugada(s)");
        totalMinutos.setText(Double.toString(partida.getTotal_segundos() / 60 ) + " Minuto(s)");
*/

        return row;
    }

}
