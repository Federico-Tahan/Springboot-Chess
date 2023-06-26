package com.example.Ajedrez.dominio;

import javax.persistence.Entity;

@Entity

public class Rey extends Pieza{
    public Rey(String color, int fila, int columna, String simbolo) {
        super(color, fila, columna,simbolo);
    }

    public Rey() {
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

        if ((diffFilas == 1 && diffColumnas == 0) || (diffFilas == 0 && diffColumnas == 1) || (diffFilas == 1 && diffColumnas == 1)) {
            Pieza piezaDestino = matriz[nuevaFila][nuevaColumna];

            if (piezaDestino != null) {
                if (piezaDestino.getColor().equals(getColor())) {
                    return false; 
                }
            }

            if (estaEnJaque(matriz,nuevaFila,nuevaColumna)) {
                return false; 
            }

            return true; 
        }

        return false; 
    }

    public boolean estaEnJaque(Pieza[][] matriz, int nuevafila, int nuevacolumna) {
        return estaEnJaquePeon(matriz, nuevafila, nuevacolumna)
                || estaEnJaqueCaballo(matriz, nuevafila, nuevacolumna)
                || estaEnJaqueAlfil(matriz, nuevafila, nuevacolumna)
                || estaEnJaqueRey(matriz, nuevafila, nuevacolumna)
                || estaEnJaqueReina(matriz, nuevafila, nuevacolumna)
                || estaEnJaqueTorre(matriz, nuevafila, nuevacolumna);
    }
    private boolean estaEnJaquePeon(Pieza[][] matriz, int filaRey, int columnaRey) {
        int[] deltasFilas = {-1, 1};
        int[] deltasColumnas = {-1, 1};

        for (int i = 0; i < deltasFilas.length; i++) {
            int filaActual = filaRey + deltasFilas[i];
            int columnaIzquierda = columnaRey + deltasColumnas[0];
            int columnaDerecha = columnaRey + deltasColumnas[1];

            if (esCasillaValida(filaActual, columnaIzquierda)) {
                Pieza pieza = matriz[filaActual][columnaIzquierda];
                if (pieza != null && !pieza.getColor().equals(getColor()) && pieza instanceof Peon) {
                    return true; 
                }
            }

            if (esCasillaValida(filaActual, columnaDerecha)) {
                Pieza pieza = matriz[filaActual][columnaDerecha];
                if (pieza != null && !pieza.getColor().equals(getColor()) && pieza instanceof Peon) {
                    return true;
                }
            }
        }

        return false;
    }


    private boolean estaEnJaqueCaballo(Pieza[][] matriz, int filaRey, int columnaRey) {


        int[] deltasFilas = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] deltasColumnas = {-1, 1, -2, 2, -2, 2, -1, 1};

        for (int i = 0; i < deltasFilas.length; i++) {
            int filaActual = filaRey + deltasFilas[i];
            int columnaActual = columnaRey + deltasColumnas[i];

            if (filaActual >= 0 && filaActual < 8 && columnaActual >= 0 && columnaActual < 8) {
                Pieza pieza = matriz[filaActual][columnaActual];
                if (pieza != null && !pieza.getColor().equals(getColor()) && pieza instanceof Caballo) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean estaEnJaqueAlfil(Pieza[][] matriz, int filaRey, int columnaRey) {
        int[] deltasFilas = {-1, -1, 1, 1};
        int[] deltasColumnas = {-1, 1, -1, 1};
        Pieza piezaJaque = null;

        for (int i = 0; i < deltasFilas.length; i++) {
            int filaActual = filaRey + deltasFilas[i];
            int columnaActual = columnaRey + deltasColumnas[i];

            while (esCasillaValida(filaActual, columnaActual)) {
                Pieza pieza = matriz[filaActual][columnaActual];
                if (pieza != null && !pieza.getColor().equals(getColor())) {
                    if (pieza instanceof Reina || pieza instanceof Alfil) {
                        piezaJaque = pieza;
                        break;
                    } else {
                        break;
                    }
                }
                filaActual += deltasFilas[i];
                columnaActual += deltasColumnas[i];
            }
        }

        if (piezaJaque != null) {
            if (piezaJaque instanceof Reina || piezaJaque instanceof Alfil) {
                int diferenciaFilas = filaRey - piezaJaque.getFila();
                int diferenciaColumnas = columnaRey - piezaJaque.getColumna();

                if(piezaJaque.getColumna() == columnaRey && piezaJaque.getFila() == filaRey)
                {
                    return true;
                }
                if (Math.abs(diferenciaFilas) == Math.abs(diferenciaColumnas)) {
                    if (diferenciaFilas > 0) {
                        deltasFilas[0] = -1;
                        deltasFilas[2] = -1;
                    } else {
                        deltasFilas[1] = 1;
                        deltasFilas[3] = 1;
                    }
                    if (diferenciaColumnas > 0) {
                        deltasColumnas[0] = -1;
                        deltasColumnas[1] = 1;
                    } else {
                        deltasColumnas[2] = -1;
                        deltasColumnas[3] = 1;
                    }
                }
            }
        }


        return piezaJaque != null;
    }

    private boolean estaEnJaqueRey(Pieza[][] matriz, int filaRey, int columnaRey) {


        int[] deltasFilas = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] deltasColumnas = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < deltasFilas.length; i++) {
            int filaActual = filaRey + deltasFilas[i];
            int columnaActual = columnaRey + deltasColumnas[i];

            if (filaActual >= 0 && filaActual < 8 && columnaActual >= 0 && columnaActual < 8) {
                Pieza pieza = matriz[filaActual][columnaActual];
                if (pieza != null && !pieza.getColor().equals(getColor()) && pieza instanceof Rey) {
                    return true;
                }
            }
        }

        return false;
    }
    private boolean esCasillaValida(int fila, int columna) {
        return fila >= 0 && fila < 8 && columna >= 0 && columna < 8;
    }
    private boolean estaEnJaqueReina(Pieza[][] matriz, int nuevaFila,int nuevaCol) {
        return estaEnJaqueAlfil(matriz,nuevaFila,nuevaCol) || estaEnJaqueTorre(matriz,nuevaFila,nuevaCol);
    }

    private boolean estaEnJaqueTorre(Pieza[][] matriz, int filaRey, int columnaRey) {
        int[] deltasFilas = {-1, 1, 0, 0};
        int[] deltasColumnas = {0, 0, -1, 1};
        Pieza piezaJaque = null;
        boolean jaqueEnLineaVertical = false;
        boolean jaqueEnLineaHorizontal = false;

        for (int i = 0; i < deltasFilas.length; i++) {
            int filaActual = filaRey + deltasFilas[i];
            int columnaActual = columnaRey + deltasColumnas[i];

            while (filaActual >= 0 && filaActual < 8 && columnaActual >= 0 && columnaActual < 8) {
                Pieza pieza = matriz[filaActual][columnaActual];
                if (pieza != null && !pieza.getColor().equals(getColor())) {
                    if (pieza instanceof Reina || pieza instanceof Torre) {
                        piezaJaque = pieza;
                        break;
                    } else {
                        break;
                    }
                }
                filaActual += deltasFilas[i];
                columnaActual += deltasColumnas[i];
            }
        }



        if (piezaJaque != null) {
            if (piezaJaque instanceof Reina || piezaJaque instanceof Torre) {
                if(piezaJaque.getFila() == filaRey && piezaJaque.getColumna() == columnaRey)
                {
                    return  true;
                }else
                    if (filaRey == piezaJaque.getFila()) {
                    jaqueEnLineaHorizontal = true;
                } else if (columnaRey == piezaJaque.getColumna()) {
                    jaqueEnLineaVertical = true;
                }
            }
        }

        return jaqueEnLineaVertical || jaqueEnLineaHorizontal;
    }
}
