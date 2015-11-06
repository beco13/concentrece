package universidad.trabajo.concentrese.core;

import java.util.ArrayList;

/**
 * Created by friendly on 02/11/2015.
 */
public class NumerosAleatorios {
    private int valorInicial;
    private int valorFinal;
    private ArrayList listaNumero;

    public NumerosAleatorios(int valorInicial, int valorFinal) {
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
        listaNumero = new ArrayList();
    }

    private int numeroAleatorio() {
        return (int) (Math.random() * (valorFinal - valorInicial + 1) + valorInicial);
    }

    public int generar() {
        System.out.println("informmacion de numeros: "+listaNumero);
        if (listaNumero.size() < (valorFinal - valorInicial) + 1) {
            //Aun no se han generado todos los numeros
            int numero = numeroAleatorio();//genero un numero
            if (listaNumero.isEmpty()) {//si la lista esta vacia
                listaNumero.add(numero);
                return numero;
            } else {//si no esta vacia
                if (listaNumero.contains(numero)) {//Si el numero que generé esta contenido en la lista
                    return generar();//recursivamente lo mando a generar otra vez
                } else {//Si no esta contenido en la lista
                    listaNumero.add(numero);
                    return numero;
                }
            }
        } else {// ya se generaron todos los numeros
            return -1;
        }
    }

    public ArrayList getListaNumero(){
        int tmpNumero ;
        do {
            tmpNumero = this.generar();
        }while (tmpNumero != -1);

        return this.listaNumero;
    }


}
