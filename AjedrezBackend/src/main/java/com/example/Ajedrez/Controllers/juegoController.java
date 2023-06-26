package com.example.Ajedrez.Controllers;

import com.example.Ajedrez.Repository.TableroRepository;
import com.example.Ajedrez.Service.juegoService;
import com.example.Ajedrez.dominio.GameStatus;
import com.example.Ajedrez.dominio.Juego;
import com.example.Ajedrez.dominio.Pieza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RestController
public class juegoController {

    private final juegoService JuegoServicio;

    @Autowired
    public juegoController(juegoService JuegoServicio){
        this.JuegoServicio = JuegoServicio;
    }

    @PostMapping("/movimiento")
    GameStatus movimientoPieza(@RequestParam("origenF") int origenF,
                               @RequestParam("origenC") int origenC,
                               @RequestParam("destinoF") int destinoF,
                               @RequestParam("destinoC") int destinoC) {
        return JuegoServicio.realizarMovimiento(origenF, origenC, destinoF, destinoC);
    }

    @GetMapping("/gameStatus")
    GameStatus obtenerEstado() {
        return JuegoServicio.estadoDejuego();
    }

    @GetMapping("/obtenerJuegos")
    List<Juego> obtenerJuegos() {
        return JuegoServicio.obtenerTodosLosJuegos();
    }


    @PostMapping("/establecerJuego")
    boolean settearJuego(@RequestParam("idJuego") Long juegoid) {
        try{
            JuegoServicio.CargarJuego(juegoid);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @PostMapping("/crearNuevoJuego")
    boolean nuevoJuego(@RequestParam("nombreBlanco") String nombreBlanco, @RequestParam("nombreNegro") String nombreNegro){
        return JuegoServicio.crearNuevoJuego(nombreBlanco,nombreNegro);
    }
    @PostMapping("/cambiarFicha")
    boolean cambiodeFicha(@RequestParam("nuevaFicha") int nombreBlanco){
        return JuegoServicio.cambiarFicha(nombreBlanco);
    }
    @GetMapping("/mostrarPiezas")
    Pieza[][] mostrarPiezas(){
        return JuegoServicio.traerTablero();
    }
    @PostMapping("/empatar")
    GameStatus empatar(@RequestParam("rtaAdversario") int rta){
        return JuegoServicio.empatar(rta);
    }

    @PostMapping("/GuardarPartida")
    void GuardarPartida(){
         JuegoServicio.guardarJuego();
    }

}
