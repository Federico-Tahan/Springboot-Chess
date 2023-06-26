package com.example.Ajedrez.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;
    @Column(name = "origenFILA")

    private int movOrigenFila;
    @Column(name = "origenCOLUMNA")

    private int movOrigenCol;
    @Column(name = "destinoCOLUMNA")

    private int movDestinoFila;
    @Column(name = "destinoFila")

    private int movDestinoCol;

    @ManyToOne
    @JoinColumn(name = "juego_id")
    @JsonIgnore
    private Juego juego;

    public Movimiento() {
    }
}
