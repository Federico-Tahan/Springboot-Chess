package com.example.Ajedrez.Service;

import com.example.Ajedrez.dominio.GameStatus;
import com.example.Ajedrez.dominio.Juego;
import com.example.Ajedrez.dominio.Pieza;
import com.example.Ajedrez.dominio.Tablero;

import java.util.List;

public interface juegoService {

    boolean crearNuevoJuego(String jugadorBlanco, String jugadorNegro);
/*
    boolean iniciarlizarTablero();
*/
    GameStatus realizarMovimiento(int origenFila, int origenColumna, int destinoFila, int destinoColumna);
    List<Juego> obtenerTodosLosJuegos();
    GameStatus estadoDejuego();
    boolean cambiarFicha(int i);
    boolean turnoDe();
    Pieza[][]  traerTablero();
    GameStatus empatar(int respuesta);
    void guardarJuego();

    void CargarJuego(Long id);
}
