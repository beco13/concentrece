package universidad.trabajo.concentrese.main;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

import universidad.trabajo.concentrese.R;
import universidad.trabajo.concentrese.core.Elemento;
import universidad.trabajo.concentrese.core.ImageButtonAdapter;
import universidad.trabajo.concentrese.core.NumerosAleatorios;


public class PlayActivity extends AppCompatActivity {

    GridView gridListaBotones;
    Elemento imgsRender[] = new Elemento[16];
    int parejasDescubiertas = 0;
    int tmpPareja = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        this.init();

        gridListaBotones = (GridView) findViewById(R.id.lista_botones);

        ImageButtonAdapter buttonListAdapter = new ImageButtonAdapter(getApplicationContext(), imgsRender);

        gridListaBotones.setAdapter(buttonListAdapter);

        gridListaBotones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ImageView tmpImageView = (ImageView) v;
                tmpImageView.setImageResource(imgsRender[position].getImgToDiscover());

                if (tmpPareja == -1) {
                    tmpPareja = position;
                } else {

                    if (imgsRender[tmpPareja].getImgToDiscover() == imgsRender[position].getImgToDiscover()) {
                        imgsRender[tmpPareja].setVisible(true);
                        imgsRender[position].setVisible(true);
                    }

                    ImageButtonAdapter tmpButtonListAdapter = new ImageButtonAdapter(getApplicationContext(), imgsRender);

                    try {
                        Thread.sleep(3000);
                        // Then do something meaningful...

                        gridListaBotones.setAdapter(tmpButtonListAdapter);
                        tmpPareja = -1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    private void init() {

        int tmpImgsResources[] = new int[]{
                R.drawable.play_abstract,
                R.drawable.play_cat,
                R.drawable.play_city,
                R.drawable.play_fashion,
                R.drawable.play_food,
                R.drawable.play_nature,
                R.drawable.play_technics,
                R.drawable.play_transport,

                R.drawable.play_abstract,
                R.drawable.play_cat,
                R.drawable.play_city,
                R.drawable.play_fashion,
                R.drawable.play_food,
                R.drawable.play_nature,
                R.drawable.play_technics,
                R.drawable.play_transport,
        };

        NumerosAleatorios numeroAzar = new NumerosAleatorios(0, 15);
        ArrayList tmpNumeros = numeroAzar.getListaNumero();


        for (int i = 0; i < 16; i++) {
            imgsRender[i] = new Elemento(tmpImgsResources[Integer.parseInt(tmpNumeros.get(i).toString())]);
        }
    }


}
