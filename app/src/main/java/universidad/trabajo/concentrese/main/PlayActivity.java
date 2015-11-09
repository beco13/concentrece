package universidad.trabajo.concentrese.main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import universidad.trabajo.concentrese.R;
import universidad.trabajo.concentrese.core.Auth;
import universidad.trabajo.concentrese.core.Elemento;
import universidad.trabajo.concentrese.core.ImageButtonAdapter;
import universidad.trabajo.concentrese.core.NumerosAleatorios;
import universidad.trabajo.concentrese.core.Partida;


public class PlayActivity extends AppCompatActivity {

    public GridView gridListaBotones;
    public Elemento imgsRender[] = new Elemento[16];
    public int parejasDescubiertas = 0;
    public int tmpPareja = -1;
    public int totalJugadas = 0;

    public int totalSeconds = 1;
    public int seconds = 0;
    public int minutes = 0;

    public boolean onProcess = false;

    Timer counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //System.out.println("Fecha sistema: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        this.startGame();
    }

    private void startGame() {

        imgsRender = new Elemento[16];
        parejasDescubiertas = 0;
        tmpPareja = -1;
        totalJugadas = 0;

        totalSeconds = 1;
        seconds = 0;
        minutes = 0;

        onProcess = false;

        this.init();

        this.startTime();

        gridListaBotones = (GridView) findViewById(R.id.lista_botones);

        ImageButtonAdapter buttonListAdapter = new ImageButtonAdapter(getApplicationContext(), imgsRender);

        gridListaBotones.setAdapter(buttonListAdapter);

        gridListaBotones.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            private void updateGrid() {

                CountDownTimer esconderElementos = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        ImageButtonAdapter tmpButtonListAdapter = new ImageButtonAdapter(getApplicationContext(), imgsRender);
                        gridListaBotones.setAdapter(tmpButtonListAdapter);
                        tmpPareja = -1;
                        onProcess = false;

                        if (parejasDescubiertas == 8) {
                            onFinishPlay();
                        }

                    }
                };

                esconderElementos.start();

            }

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (onProcess == false & imgsRender[position].getUncovered() == false & (imgsRender[position].getVisible() == false || tmpPareja != position)) {
                    totalJugadas++;
                    ImageView tmpImageView = (ImageView) v;
                    tmpImageView.setImageResource(imgsRender[position].getImgToDiscover());

                    if (tmpPareja == -1) {
                        tmpPareja = position;
                        imgsRender[position].setVisible(true);
                    } else {
                        onProcess = true;
                        if (imgsRender[tmpPareja].getImgToDiscover() == imgsRender[position].getImgToDiscover()) {
                            imgsRender[tmpPareja].setVisible(true);
                            imgsRender[position].setVisible(true);

                            imgsRender[tmpPareja].setUncovered(true);
                            imgsRender[position].setUncovered(true);
                            parejasDescubiertas++;
                        } else {
                            imgsRender[tmpPareja].setVisible(false);
                        }
                        this.updateGrid();
                    }
                    actualizarJugadas();
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

    private void startTime() {
        counter = new Timer();
        counter.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    private String toFormatSeconds() {
                        if (Integer.toString(seconds).length() == 1) {
                            return "0" + String.valueOf(seconds);
                        } else {
                            return String.valueOf(seconds);
                        }
                    }

                    private String toFormatMinutes() {
                        if (Integer.toString(minutes).length() == 1) {
                            return "0" + String.valueOf(minutes);
                        } else {
                            return String.valueOf(minutes);
                        }
                    }

                    @Override
                    public void run() {
                        TextView textTiempo = (TextView) findViewById(R.id.text_tiempo);
                        textTiempo.setText(this.toFormatMinutes() + ":" + this.toFormatSeconds());
                        seconds += 1;
                        totalSeconds += 1;
                        if (seconds == 60) {
                            seconds = 0;
                            minutes += 1;
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    public void onFinishPlay() {
        counter.cancel();

        Auth tmpSession = new Auth();
        tmpSession.toLoad(getApplicationContext());

        Partida tmpPartida = new Partida(Integer.parseInt(tmpSession.getUserId()), this.totalSeconds, this.totalJugadas);

        tmpPartida.toSave();


        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
        builder
                .setMessage("¿Desea volver a jugar?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        startGame();
                        actualizarJugadas();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        toMainActivity();
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog tmpDialog = builder.create();
        tmpDialog.show();
    }

    private void toMainActivity(){
        super.onBackPressed();
    }

    private void actualizarJugadas(){
        TextView textJugadas = (TextView) findViewById(R.id.text_jugadas);
        textJugadas.setText(Integer.toString(totalJugadas));
    }
}
