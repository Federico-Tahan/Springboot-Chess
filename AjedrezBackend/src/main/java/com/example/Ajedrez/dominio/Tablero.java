package com.example.Ajedrez.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "Tablero")

public class Tablero implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "tablero_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "juego_id")
    private Juego juego;

    @OneToMany(mappedBy = "tablero", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Pieza> piezas;

    @Transient
    private Pieza[][] tablero;

    public void cargarTablero() {
        tablero = new Pieza[8][8];

        for (Pieza pieza : piezas) {
            int fila = pieza.getFila();
            int columna = pieza.getColumna();

            if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
                tablero[fila][columna] = pieza;
            } else {
            }
        }
    }
    public void copiarPiezasDesdeMatriz(Pieza[][] matriz) {
        piezas = new ArrayList<>();

        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz[fila].length; columna++) {
                Pieza pieza = matriz[fila][columna];
                if (pieza != null) {
                    piezas.add(pieza);
                    pieza.setTablero(this);
                }
            }
        }
    }

    public Tablero() {
        tablero = new Pieza[8][8];
    }

    public void inicializarTablero() {

        for (int columna = 0; columna < 8; columna++) {
            tablero[1][columna] = new Peon("negro", 1, columna,"Peon");
        }

        for (int columna = 0; columna < 8; columna++) {
            tablero[6][columna] = new Peon("blanco", 6, columna,"Peon");
        }

        tablero[0][0] = new Torre("negro", 0, 0,"Torre");
        tablero[0][7] = new Torre("negro", 0, 7,"Torre");
        tablero[0][1] = new Caballo("negro", 0, 1,"Caballo");
        tablero[0][6] = new Caballo("negro", 0, 6,"Caballo");
        tablero[0][2] = new Alfil("negro", 0, 2,"Alfil");
        tablero[0][5] = new Alfil("negro", 0, 5,"Alfil");
        tablero[0][3] = new Reina("negro", 0, 3,"Reina");
        tablero[0][4] = new Rey("negro", 0, 4,"Rey");

        tablero[7][0] = new Torre("blanco", 7, 0,"Torre");
        tablero[7][7] = new Torre("blanco", 7, 7,"Torre");
        tablero[7][1] = new Caballo("blanco", 7, 1,"Caballo");
        tablero[7][6] = new Caballo("blanco", 7, 6,"Caballo");
        tablero[7][2] = new Alfil("blanco", 7, 2,"Alfil");
        tablero[7][5] = new Alfil("blanco", 7, 5,"Alfil");
        tablero[7][3] = new Reina("blanco", 7, 3,"Reina");
        tablero[7][4] = new Rey("blanco", 7, 4,"Rey");
        obtenerMovimientosPosiblesEnTablero();
        copiarPiezasDesdeMatriz(tablero);
    }


    public void obtenerMovimientosPosiblesEnTablero() {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                Pieza pieza = tablero[fila][columna];
                if (pieza != null) {
                    pieza.obtenerMovimientosPosibles(tablero);
                }
            }
        }
    }
    public Pieza getPieza(int fila, int columna) {
        if (esPosicionValida(fila, columna)) {
            return tablero[fila][columna];
        } else {
            return null;
        }
    }

    public void setPieza(int fila, int columna, Pieza pieza) {
        if (esPosicionValida(fila, columna)) {
            tablero[fila][columna] = pieza;
        }
    }

    public List<Pieza> getPiezasBlancas() {
        List<Pieza> piezasBlancas = new ArrayList<>();
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                Pieza pieza = getPieza(fila, columna);
                if (pieza != null && pieza.getColor().equals("blanco")) {
                    piezasBlancas.add(pieza);
                }
            }
        }
        return piezasBlancas;
    }

    public List<Pieza> getPiezasNegras() {
        List<Pieza> piezasNegras = new ArrayList<>();
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                Pieza pieza = getPieza(fila, columna);
                if (pieza != null && pieza.getColor().equals("negro")) {
                    piezasNegras.add(pieza);
                }
            }
        }
        return piezasNegras;
    }

    public Pieza[][] imprimirTablero(){
        return tablero;
    }


    public void settearTablero(Pieza[][] t){
        tablero = t;
    }

    public boolean esPosicionValida(int fila, int columna) {
        return fila >= 0 && fila < 8 && columna >= 0 && columna < 8;
    }

    public Pieza obtenerRey(String colorRey) {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                Pieza pieza = getPieza(fila, columna);
                if (pieza instanceof Rey && pieza.getColor().equals(colorRey)) {
                    return pieza;
                }
            }
        }
        return null;
    }

    public Pieza obtenerPiezaAmenazante(Pieza reyEnemigo, String colorReyEnemigo) {
        List<Pieza> piezasAliadas = colorReyEnemigo.equals("blanco") ? getPiezasNegras() : getPiezasBlancas();

        for (Pieza pieza : piezasAliadas) {
            List<Posicion> movimientosPosibles = pieza.obtenerMovimientosPosibles(imprimirTablero());
            for (Posicion posicion : movimientosPosibles) {
                if (posicion.getFila() == reyEnemigo.getFila() && posicion.getColumna() == reyEnemigo.getColumna()) {
                    return pieza;
                }
            }
        }

        return null;
    }
}