package com.example.Ajedrez.dominio;

import javax.persistence.Entity;

@Entity

public class Alfil extends Pieza{
    public Alfil(String color, int fila, int columna, String simbolo) {
        super(color, fila, columna,simbolo);
    }

    public Alfil() {
    }

    @Override
    public boolean esMovimientoValido(int nuevaFila, int nuevaColumna, Pieza[][] matriz) {
        int filaActual = getFila();
        int columnaActual = getColumna();

        int diffFilas = Math.abs(nuevaFila - filaActual);
        int diffColumnas = Math.abs(nuevaColumna - columnaActual);

        if (diffFilas == diffColumnas) {
            int filaIncremento = (nuevaFila > filaActual) ? 1 : -1;
            int columnaIncremento = (nuevaColumna > columnaActual) ? 1 : -1;

            int fila = filaActual + filaIncremento;
            int columna = columnaActual + columnaIncremento;

            while (fila != nuevaFila && columna != nuevaColumna) {
                if (fila < 0 || fila >= matriz.length || columna < 0 || columna >= matriz[0].length) {
                    return false;
                }

                if (matriz[fila][columna] != null) {
                    return false;
                }

                fila += filaIncremento;
                columna += columnaIncremento;
            }

            if (matriz[nuevaFila][nuevaColumna] == null) {
                return true;
            } else if (!matriz[nuevaFila][nuevaColumna].getColor().equals(getColor())) {
                return true;
            }
        }

        return false;
    }
}