package com.example.Ajedrez.dominio;

import javax.persistence.Entity;

@Entity

public class Torre extends Pieza {
    public Torre(String color, int fila, int columna, String simbolo) {
        super(color, fila, columna,simbolo);
    }

    public Torre() {
    }

    @Override
    public boolean esMovimientoValido(int nuevaFila, int nuevaColumna, Pieza[][] matriz) {
        int filaActual = getFila();
        int columnaActual = getColumna();

        if (filaActual == nuevaFila && columnaActual == nuevaColumna) {
            return false;
        }

        int diffFilas = Math.abs(nuevaFila - filaActual);
        int diffColumnas = Math.abs(nuevaColumna - columnaActual);

        if ((diffFilas > 0 && diffColumnas == 0) || (diffFilas == 0 && diffColumnas > 0)) {
            int pasoFilas = nuevaFila > filaActual ? 1 : (nuevaFila < filaActual ? -1 : 0);
            int pasoColumnas = nuevaColumna > columnaActual ? 1 : (nuevaColumna < columnaActual ? -1 : 0);

            int fila = filaActual + pasoFilas;
            int columna = columnaActual + pasoColumnas;

            while (fila != nuevaFila || columna != nuevaColumna) {
                if (fila < 0 || fila >= matriz.length || columna < 0 || columna >= matriz[0].length) {
                    return false;
                }

                if (matriz[fila][columna] != null) {
                    return false;
                }

                fila += pasoFilas;
                columna += pasoColumnas;
            }

            Pieza piezaDestino = matriz[nuevaFila][nuevaColumna];

            if (piezaDestino != null) {
                if (piezaDestino.getColor().equals(getColor())) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}