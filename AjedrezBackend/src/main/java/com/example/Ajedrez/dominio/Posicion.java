package com.example.Ajedrez.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fila")
    private int fila;

    @Column(name = "columna")
    private int columna;

    @ManyToOne
    @JoinColumn(name = "pieza_id")
    @JsonIgnore
    private Pieza pieza;

    public Posicion(int fila, int columna, Pieza pieza) {
        this.fila = fila;
        this.columna = columna;
        this.pieza = pieza;
    }

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }
    public Posicion() {
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}