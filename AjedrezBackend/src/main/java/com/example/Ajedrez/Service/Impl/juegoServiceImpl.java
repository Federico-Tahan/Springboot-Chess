package com.example.Ajedrez.Service.Impl;

import com.example.Ajedrez.Repository.JuegoRepository;
import com.example.Ajedrez.Service.juegoService;
import com.example.Ajedrez.dominio.GameStatus;
import com.example.Ajedrez.dominio.Juego;
import com.example.Ajedrez.dominio.Pieza;
import com.example.Ajedrez.dominio.Tablero;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class juegoServiceImpl implements juegoService {

    private GameStatus gmStatus;
    private Juego juego;
    private final JuegoRepository juegoRepository;

    public juegoServiceImpl(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }

    public List<Juego> obtenerTodosLosJuegos() {
        return juegoRepository.findAll();
    }

    public void CargarJuego(Long id) {
        Optional<Juego> juegoOptional = juegoRepository.findById(id);

        if (juegoOptional.isPresent()) {
            juego = juegoOptional.get();
            juego.CargarTablero();
        } else {
        }
    }



    public void guardarJuego() {
        juegoRepository.save(this.juego);
    }
    @Override
    public boolean crearNuevoJuego(String jugadorBlanco, String jugadorNegro) {
        try {
            juego = new Juego();
            juego.setJBlanco(jugadorBlanco);
            juego.setJNegro(jugadorNegro);
            juego.iniciarJuego();
            return true;

        }catch (Exception e){
            return false;
        }
    }

    @Override
    public GameStatus realizarMovimiento(int origenFila, int origenColumna, int destinoFila, int destinoColumna) {
        gmStatus = new GameStatus();
        gmStatus.setNameJugadorBlanco(juego.obtenerNameJugadorBlanco());
        gmStatus.setNameJugadorNegro(juego.obtenerNameJugadorNegro());
        return juego.moverPieza(origenFila,origenColumna,destinoFila,destinoColumna,gmStatus);
    }
    @Override
    public GameStatus estadoDejuego() {
        gmStatus = new GameStatus();
        gmStatus.setTurnoBlanco(juego.turnoDe());
        gmStatus.setJaqueMate(juego.hayJaqueMate());
        gmStatus.setJaque(juego.hayJaque());
        gmStatus.setReyJaqueado(juego.piezaJaqueada());
        gmStatus.setEnCurso(juego.enCurso());
        gmStatus.setListaMovimientos(juego.obtenerMovimientos());
        gmStatus.setNameJugadorBlanco(juego.obtenerNameJugadorBlanco());
        gmStatus.setNameJugadorNegro(juego.obtenerNameJugadorNegro());
        return gmStatus;
    }

    @Override
    public boolean cambiarFicha(int i) {
        gmStatus = new GameStatus();

        return  juego.cambiarFicha(i,gmStatus);
    }

    @Override
    public boolean turnoDe() {
        return  juego.turnoDe();
    }

    @Override
    public Pieza[][] traerTablero() {
        try{
            return juego.traerTablero();
        }catch (Exception e){
            return  new Pieza[8][8];
        }
    }

    @Override
    public GameStatus empatar(int respuesta)
    {   
        juego.empatar(respuesta);
        GameStatus gS = estadoDejuego();
        return gS;
    }

}
