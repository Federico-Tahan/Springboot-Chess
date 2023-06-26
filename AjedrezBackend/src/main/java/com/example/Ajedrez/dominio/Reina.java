package com.example.Ajedrez.dominio;

import javax.persistence.Entity;

@Entity

public class Reina extends Pieza {

    public Reina(String color, int fila, int columna, String simbolo) {
        super(color, fila, columna,simbolo);
    }

    public Reina() {
    }

    @Override
    public boolean esMovimientoValido(int nuevaFila, int nuevaColumna, Pieza[][] matriz) {
        int filaActual = getFila();
        int columnaActual = getColumna();

        if (filaActual == nuevaFila && columnaActual == nuevaColumna) {
            return false; // La reina no puede quedarse en la misma posición
        }

        int diffFilas = Math.abs(nuevaFila - filaActual);
        int diffColumnas = Math.abs(nuevaColumna - columnaActual);

        if ((diffFilas > 0 && diffColumnas == 0) || (diffFilas == 0 && diffColumnas > 0) || (diffFilas == diffColumnas)) {
            int pasoFilas = nuevaFila > filaActual ? 1 : (nuevaFila < filaActual ? -1 : 0);
            int pasoColumnas = nuevaColumna > columnaActual ? 1 : (nuevaColumna < columnaActual ? -1 : 0);

            int fila = filaActual + pasoFilas;
            int columna = columnaActual + pasoColumnas;

            while (fila != nuevaFila || columna != nuevaColumna) {
                if (fila < 0 || fila >= matriz.length || columna < 0 || columna >= matriz[0].length) {
                    return false; // Movimiento fuera del tablero
                }

                if (matriz[fila][columna] != null) {
                    return false; // No se puede poner una pieza en el camino
                }

                fila += pasoFilas;
                columna += pasoColumnas;
            }

            Pieza piezaDestino = matriz[nuevaFila][nuevaColumna];

            if (piezaDestino != null) {
                if (piezaDestino.getColor().equals(getColor())) {
                    return false; // No se puede comer una pieza del mismo color
                }
            }

            return true; // Movimiento válido
        }

        return false; // Movimiento inválido para la reina
    }
}