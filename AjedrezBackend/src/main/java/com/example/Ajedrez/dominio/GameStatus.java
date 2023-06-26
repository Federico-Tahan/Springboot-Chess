package com.example.Ajedrez.dominio;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameStatus {
        private boolean enCurso;
        private boolean turnoBlanco;
        private String nameJugadorBlanco;
        private String nameJugadorNegro;
        private boolean jaque;
        private boolean jaqueMate;
        private Pieza reyJaqueado;
        private String ganador;
        private boolean movAprobado;
        private  boolean CoronarPeon;
        private Pieza[][] tablero;
        private Set<Movimiento> listaMovimientos = new HashSet<>();
}
