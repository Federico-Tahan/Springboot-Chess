package com.example.Ajedrez.dominio;

import javax.persistence.Entity;

@Entity

public class Caballo extends Pieza {
    public Caballo(String color, int fila, int columna, String simbolo) {
        super(color, fila, columna,simbolo);
    }

    public Caballo() {
    }

    @Override
    public boolean esMovimientoValido(int nuevaFila, int nuevaColumna, Pieza[][] matriz) {
        int filaActual = getFila();
        int columnaActual = getColumna();

        int diffFilas = Math.abs(nuevaFila - filaActual);
        int diffColumnas = Math.abs(nuevaColumna - columnaActual);

        if ((diffFilas == 2 && diffColumnas == 1) || (diffFilas == 1 && diffColumnas == 2)) {
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