package com.example.Ajedrez.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Pieza implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "pieza_id")
    private Long id;

    @Column(name = "color")
    private String color;

    @Column(name = "fila")
    private int fila;

    @Column(name = "columna")
    private int columna;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "tablero_id")
    @JsonIgnore
    private Tablero tablero;

    @OneToMany(mappedBy = "pieza", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Posicion> movsPosibles;


    protected Pieza() {
    }
    public Pieza(String color, int fila, int columna, String nombre) {
        this.color = color;
        this.fila = fila;
        this.columna = columna;
        this.nombre = nombre;
    }

    public abstract boolean esMovimientoValido(int nuevaFila, int nuevaColumna, Pieza[][] matriz);
    public List<Posicion> obtenerMovimientosPosibles(Pieza[][] tablero) {
        List<Posicion> movimientosPosibles = new ArrayList<>();

        int filas = tablero.length;
        int columnas = tablero[0].length;

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                if (esMovimientoValido(fila, columna, tablero)) {
                    movimientosPosibles.add(new Posicion(fila, columna,this));
                }
            }
        }
        movsPosibles = movimientosPosibles;
        return movimientosPosibles;
    }

}
