package universidad.trabajo.concentrese.core;

import java.util.*;

/**
 * Created by friendly on 06/10/2015.
 */

public class Partida {

    private int id;
    private Date created;
    private Date fecha_inicio;
    private Date fecha_fin;
    private int total_segundos;
    private int total_jugadas;
    private Jugador jugador;

    public Partida( Date created, Date fecha_inicio, Date fecha_fin, int total_segundos, int total_jugadas, Jugador jugador) {
        this.created = created;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.total_segundos = total_segundos;
        this.total_jugadas = total_jugadas;
        this.jugador = jugador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public int getTotal_segundos() {
        return total_segundos;
    }

    public void setTotal_segundos(int total_segundos) {
        this.total_segundos = total_segundos;
    }

    public int getTotal_jugadas() {
        return total_jugadas;
    }

    public void setTotal_jugadas(int total_jugadas) {
        this.total_jugadas = total_jugadas;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}
