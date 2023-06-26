package com.example.Ajedrez.dominio;

import javax.persistence.Entity;

@Entity

public class Peon extends Pieza{
    public Peon(String color, int fila, int columna, String simbolo) {
        super(color, fila, columna,simbolo);
    }

    public Peon() {
    }

    @Override
    public boolean esMovimientoValido(int nuevaFila, int nuevaColumna, Pieza[][] matriz) {
        int filaActual = getFila();
        int columnaActual = getColumna();

        if (getColor().equals("blanco")) {
            if (nuevaFila == filaActual - 1 && nuevaColumna == columnaActual && matriz[nuevaFila][nuevaColumna] == null) {
                return true;
            }
            if (filaActual == 6 && nuevaFila == filaActual - 2 && nuevaColumna == columnaActual && matriz[nuevaFila][nuevaColumna] == null) {
                if (matriz[filaActual - 1][columnaActual] != null) {
                    return false;
                }
                return true;
            }
            if (nuevaFila == filaActual - 1 && nuevaColumna == columnaActual - 1 && matriz[nuevaFila][nuevaColumna] != null && matriz[nuevaFila][nuevaColumna].getColor().equals("negro")) {
                return true;
            }
            if (nuevaFila == filaActual - 1 && nuevaColumna == columnaActual + 1 && matriz[nuevaFila][nuevaColumna] != null && matriz[nuevaFila][nuevaColumna].getColor().equals("negro")) {
                return true;
            }
        }

        if (getColor().equals("negro")) {
            if (nuevaFila == filaActual + 1 && nuevaColumna == columnaActual && matriz[nuevaFila][nuevaColumna] == null) {
                return true;
            }
            if (filaActual == 1 && nuevaFila == filaActual + 2 && nuevaColumna == columnaActual && matriz[nuevaFila][nuevaColumna] == null) {
                if (matriz[filaActual + 1][columnaActual] != null) {
                    return false;
                }
                return true;
            }
            if (nuevaFila == filaActual + 1 && nuevaColumna == columnaActual - 1 && matriz[nuevaFila][nuevaColumna] != null && matriz[nuevaFila][nuevaColumna].getColor().equals("blanco")) {
                return true;
            }
            if (nuevaFila == filaActual + 1 && nuevaColumna == columnaActual + 1 && matriz[nuevaFila][nuevaColumna] != null && matriz[nuevaFila][nuevaColumna].getColor().equals("blanco")) {
                return true;
            }
        }

        return false;
    }


}
