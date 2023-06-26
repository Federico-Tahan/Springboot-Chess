package com.example.Ajedrez.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "Juego")
public class Juego implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "juego_id")
    private Long id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Tablero tablero;
    @Column(name = "jugador_blanco_id")
    private String jBlanco;
    @Column(name = "jugador_negro_id")
    private String jNegro;
    private boolean turnoBlanco;
    private boolean enCurso = true;
    private boolean estadojaque;
    private boolean estadoJaqueMate;
    @OneToOne
    @JoinColumn(name = "rey_jaqueado_id")
    private Pieza reyJaqueado;
    private boolean empataron = false;
    @OneToMany(mappedBy = "juego", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.EAGER)
    private Set<Movimiento> listaMovimientos = new HashSet<Movimiento>();
    public Juego() {
        tablero = new Tablero();
        turnoBlanco = true;
        tablero.setJuego(this);
    }

    public Pieza[][]  traerTablero(){
       return tablero.imprimirTablero();
    }
    public boolean iniciarJuego() {
            tablero.inicializarTablero();
            return true;
    }

    public boolean validarMovimiento(int origenFila, int origenColumna, int destinoFila, int destinoColumna) {
        Pieza piezaOrigen = tablero.getPieza(origenFila,origenColumna);



        if (piezaOrigen == null) {
            return false;
        }

        if(Objects.equals(piezaOrigen.getColor(), "blanco") && !turnoBlanco || Objects.equals(piezaOrigen.getColor(), "negro") && turnoBlanco ) {
            return false;
        }

        if (!piezaOrigen.esMovimientoValido(destinoFila, destinoColumna,tablero.imprimirTablero())) {
            return false;
        }

        return true;
    }
    public  GameStatus moverPieza(int origenFila, int origenColumna, int destinoFila, int destinoColumna, GameStatus gmStatus) {
        Pieza pieza = tablero.getPieza(origenFila, origenColumna);

        if (validarMovimiento(origenFila, origenColumna, destinoFila, destinoColumna))
        {
            System.out.println("Movimiento totalmente valido.");
            pieza.setFila(destinoFila);
            pieza.setColumna(destinoColumna);
            tablero.setPieza(destinoFila, destinoColumna, pieza);
            tablero.setPieza(origenFila, origenColumna, null);
            tablero.obtenerMovimientosPosiblesEnTablero();
            turnoBlanco = !turnoBlanco;
            gmStatus.setCoronarPeon(verificarPosicionPieza(pieza));
            gmStatus.setMovAprobado(true);
            gmStatus.setTurnoBlanco(turnoBlanco);
            gmStatus.setTablero(tablero.imprimirTablero());
            Movimiento Movimiento = new Movimiento();
            Movimiento.setMovOrigenFila(origenFila);
            Movimiento.setMovDestinoCol(origenColumna);
            Movimiento.setMovDestinoFila(destinoFila);
            Movimiento.setMovDestinoCol(destinoColumna);
            Movimiento.setJuego(this);
            listaMovimientos.add(Movimiento);
            tablero.copiarPiezasDesdeMatriz(tablero.imprimirTablero());

        } else {
            System.out.println("Movimiento inválido. Intenta nuevamente.");
           gmStatus.setMovAprobado(false);
        }


        try
        {
            if(pieza != null){
                if(pieza.getColor() == "negro")
                {
                    if(jaqueMate("blanco",tablero)){
                        enCurso = false;
                        System.out.println("JAqueeee MATEEEEEEEE");
                        gmStatus.setJaqueMate(true);
                        gmStatus.setEnCurso(false);
                        gmStatus.setGanador("negro");
                        gmStatus.setReyJaqueado(tablero.obtenerRey("blanco"));
                        estadoJaqueMate = true;
                        reyJaqueado = tablero.obtenerRey("blanco");
                        gmStatus.setTablero(tablero.imprimirTablero());

                    }else {
                        if(jaque("blanco" ,tablero)){
                            System.out.println("JAqueeee");
                            gmStatus.setJaque(true);
                            gmStatus.setReyJaqueado(tablero.obtenerRey("blanco"));
                            estadojaque = true;
                            reyJaqueado = tablero.obtenerRey("blanco");
                            gmStatus.setTablero(tablero.imprimirTablero());

                        }else {
                            estadoJaqueMate = false;
                            estadojaque = false;
                            reyJaqueado = null;
                        }
                    }

                }

                else if (Objects.equals(pieza.getColor(), "blanco"))
                {
                    if(jaqueMate("negro",tablero)){
                        enCurso = false;

                        System.out.println("JAqueeee MATEEEEEEEE");
                        gmStatus.setEnCurso(false);
                        gmStatus.setJaqueMate(true);
                        gmStatus.setGanador("blanco");
                        gmStatus.setReyJaqueado(tablero.obtenerRey("negro"));
                        estadoJaqueMate = true;
                        reyJaqueado = tablero.obtenerRey("negro");
                        gmStatus.setTablero(tablero.imprimirTablero());

                    }else {
                        if(jaque("negro",tablero)){
                            System.out.println("JAqueeee");
                            gmStatus.setJaque(true);
                            gmStatus.setReyJaqueado(tablero.obtenerRey("negro"));
                            estadojaque = true;
                            reyJaqueado = tablero.obtenerRey("negro");
                            gmStatus.setTablero(tablero.imprimirTablero());

                        }else {
                            estadoJaqueMate = false;
                            estadojaque = false;
                            reyJaqueado = null;
                        }
                    }

                }
            }
        }catch (Exception e)
        {

        }

        gmStatus.setListaMovimientos(listaMovimientos);
        gmStatus.setEnCurso(enCurso);
        return  gmStatus;
    }

    public boolean cambiarFicha(int ficha, GameStatus gmStatus){

        if(enCurso)
        {
            for (Pieza[] p: tablero.imprimirTablero()) {
                for(Pieza pieza : p)
                {
                    if(pieza instanceof Peon && (pieza.getFila() == 7 || pieza.getFila() == 0))
                    {
                        if(ficha == 1)
                        {
                            tablero.setPieza(pieza.getFila(), pieza.getColumna(), new Caballo(pieza.getColor(),pieza.getFila(), pieza.getColumna(), "Caballo"));
                            if(pieza.getColor() == "negro")
                            {
                                if(jaqueMate("blanco",tablero)){
                                    System.out.println("JAqueeee MATEEEEEEEE");
                                    gmStatus.setJaqueMate(true);
                                    gmStatus.setGanador("negro");
                                    gmStatus.setEnCurso(false);
                                    gmStatus.setReyJaqueado(tablero.obtenerRey("blanco"));
                                    estadoJaqueMate = true;
                                    reyJaqueado = tablero.obtenerRey("blanco");
                                    gmStatus.setTablero(tablero.imprimirTablero());
    
                                }else {
                                    if(jaque("blanco" ,tablero)){
                                        System.out.println("JAqueeee");
                                        gmStatus.setJaque(true);
                                        gmStatus.setReyJaqueado(tablero.obtenerRey("blanco"));
                                        estadojaque = true;
                                        reyJaqueado = tablero.obtenerRey("blanco");
                                        gmStatus.setTablero(tablero.imprimirTablero());
    
                                    }else {
                                        estadoJaqueMate = false;
                                        estadojaque = false;
                                        reyJaqueado = null;
                                    }
                                }
    
                            }
    
                            else if (Objects.equals(pieza.getColor(), "blanco"))
                            {
                                if(jaqueMate("negro",tablero)){
                                    System.out.println("JAqueeee MATEEEEEEEE");
                                    gmStatus.setJaqueMate(true);
                                    gmStatus.setGanador("blanco");
                                    gmStatus.setReyJaqueado(tablero.obtenerRey("negro"));
                                    estadoJaqueMate = true;
                                    gmStatus.setEnCurso(false);
                                    reyJaqueado = tablero.obtenerRey("negro");
                                    gmStatus.setTablero(tablero.imprimirTablero());
    
                                }else {
                                    if(jaque("negro",tablero)){
                                        System.out.println("JAqueeee");
                                        gmStatus.setJaque(true);
                                        gmStatus.setReyJaqueado(tablero.obtenerRey("negro"));
                                        estadojaque = true;
                                        reyJaqueado = tablero.obtenerRey("negro");
                                        gmStatus.setTablero(tablero.imprimirTablero());
    
                                    }else {
                                        estadoJaqueMate = false;
                                        estadojaque = false;
                                        reyJaqueado = null;
                                    }
                                }
    
                            }
                            return true;
                        }else if(ficha == 2)
                        {
                            tablero.setPieza(pieza.getFila(), pieza.getColumna(), new Alfil(pieza.getColor(),pieza.getFila(), pieza.getColumna(), "Alfil"));
                            if(pieza.getColor() == "negro")
                            {
                                if(jaqueMate("blanco",tablero)){
                                    System.out.println("JAqueeee MATEEEEEEEE");
                                    gmStatus.setJaqueMate(true);
                                    gmStatus.setGanador("negro");
                                    gmStatus.setReyJaqueado(tablero.obtenerRey("blanco"));
                                    estadoJaqueMate = true;
                                    reyJaqueado = tablero.obtenerRey("blanco");
                                    gmStatus.setTablero(tablero.imprimirTablero());
    
                                }else {
                                    if(jaque("blanco" ,tablero)){
                                        System.out.println("JAqueeee");
                                        gmStatus.setJaque(true);
                                        gmStatus.setReyJaqueado(tablero.obtenerRey("blanco"));
                                        estadojaque = true;
                                        reyJaqueado = tablero.obtenerRey("blanco");
                                        gmStatus.setTablero(tablero.imprimirTablero());
    
                                    }else {
                                        estadoJaqueMate = false;
                                        estadojaque = false;
                                        reyJaqueado = null;
                                    }
                                }
    
                            }
    
                            else if (Objects.equals(pieza.getColor(), "blanco"))
                            {
                                if(jaqueMate("negro",tablero)){
                                    System.out.println("JAqueeee MATEEEEEEEE");
                                    gmStatus.setJaqueMate(true);
                                    gmStatus.setGanador("blanco");
                                    gmStatus.setReyJaqueado(tablero.obtenerRey("negro"));
                                    estadoJaqueMate = true;
                                    reyJaqueado = tablero.obtenerRey("negro");
                                    gmStatus.setTablero(tablero.imprimirTablero());
    
                                }else {
                                    if(jaque("negro",tablero)){
                                        System.out.println("JAqueeee");
                                        gmStatus.setJaque(true);
                                        gmStatus.setReyJaqueado(tablero.obtenerRey("negro"));
                                        estadojaque = true;
                                        reyJaqueado = tablero.obtenerRey("negro");
                                        gmStatus.setTablero(tablero.imprimirTablero());
    
                                    }else {
                                        estadoJaqueMate = false;
                                        estadojaque = false;
                                        reyJaqueado = null;
                                    }
                                }
    
                            }
                            return true;
                        }else if(ficha == 3)
                        {
                            tablero.setPieza(pieza.getFila(), pieza.getColumna(), new Torre(pieza.getColor(),pieza.getFila(), pieza.getColumna(), "Torre"));
                            if(pieza.getColor() == "negro")
                            {
                                if(jaqueMate("blanco",tablero)){
                                    System.out.println("JAqueeee MATEEEEEEEE");
                                    gmStatus.setJaqueMate(true);
                                    gmStatus.setGanador("negro");
                                    gmStatus.setReyJaqueado(tablero.obtenerRey("blanco"));
                                    estadoJaqueMate = true;
                                    reyJaqueado = tablero.obtenerRey("blanco");
                                    gmStatus.setTablero(tablero.imprimirTablero());
    
                                }else {
                                    if(jaque("blanco" ,tablero)){
                                        System.out.println("JAqueeee");
                                        gmStatus.setJaque(true);
                                        gmStatus.setReyJaqueado(tablero.obtenerRey("blanco"));
                                        estadojaque = true;
                                        reyJaqueado = tablero.obtenerRey("blanco");
                                        gmStatus.setTablero(tablero.imprimirTablero());
    
                                    }else {
                                        estadoJaqueMate = false;
                                        estadojaque = false;
                                        reyJaqueado = null;
                                    }
                                }
    
                            }
    
                            else if (Objects.equals(pieza.getColor(), "blanco"))
                            {
                                if(jaqueMate("negro",tablero)){
                                    System.out.println("JAqueeee MATEEEEEEEE");
                                    gmStatus.setJaqueMate(true);
                                    gmStatus.setGanador("blanco");
                                    gmStatus.setReyJaqueado(tablero.obtenerRey("negro"));
                                    estadoJaqueMate = true;
                                    reyJaqueado = tablero.obtenerRey("negro");
                                    gmStatus.setTablero(tablero.imprimirTablero());
    
                                }else {
                                    if(jaque("negro",tablero)){
                                        System.out.println("JAqueeee");
                                        gmStatus.setJaque(true);
                                        gmStatus.setReyJaqueado(tablero.obtenerRey("negro"));
                                        estadojaque = true;
                                        reyJaqueado = tablero.obtenerRey("negro");
                                        gmStatus.setTablero(tablero.imprimirTablero());
    
                                    }else {
                                        estadoJaqueMate = false;
                                        estadojaque = false;
                                        reyJaqueado = null;
                                    }
                                }
    
                            }
                            return true;
                        }else if(ficha == 4)
                        {
                            tablero.setPieza(pieza.getFila(), pieza.getColumna(), new Reina(pieza.getColor(),pieza.getFila(), pieza.getColumna(), "Reina"));
                            if(pieza.getColor() == "negro")
                            {
                                if(jaqueMate("blanco",tablero)){
                                    System.out.println("JAqueeee MATEEEEEEEE");
                                    gmStatus.setJaqueMate(true);
                                    gmStatus.setGanador("negro");
                                    gmStatus.setReyJaqueado(tablero.obtenerRey("blanco"));
                                    estadoJaqueMate = true;
                                    reyJaqueado = tablero.obtenerRey("blanco");
                                    gmStatus.setTablero(tablero.imprimirTablero());
    
                                }else {
                                    if(jaque("blanco" ,tablero)){
                                        System.out.println("JAqueeee");
                                        gmStatus.setJaque(true);
                                        gmStatus.setReyJaqueado(tablero.obtenerRey("blanco"));
                                        estadojaque = true;
                                        reyJaqueado = tablero.obtenerRey("blanco");
                                        gmStatus.setTablero(tablero.imprimirTablero());
    
                                    }else {
                                        estadoJaqueMate = false;
                                        estadojaque = false;
                                        reyJaqueado = null;
                                    }
                                }
    
                            }
    
                            else if (Objects.equals(pieza.getColor(), "blanco"))
                            {
                                if(jaqueMate("negro",tablero)){
                                    System.out.println("JAqueeee MATEEEEEEEE");
                                    gmStatus.setJaqueMate(true);
                                    gmStatus.setGanador("blanco");
                                    gmStatus.setReyJaqueado(tablero.obtenerRey("negro"));
                                    estadoJaqueMate = true;
                                    reyJaqueado = tablero.obtenerRey("negro");
                                    gmStatus.setTablero(tablero.imprimirTablero());
    
                                }else {
                                    if(jaque("negro",tablero)){
                                        System.out.println("JAqueeee");
                                        gmStatus.setJaque(true);
                                        gmStatus.setReyJaqueado(tablero.obtenerRey("negro"));
                                        estadojaque = true;
                                        reyJaqueado = tablero.obtenerRey("negro");
                                        gmStatus.setTablero(tablero.imprimirTablero());
    
                                    }else {
                                        estadoJaqueMate = false;
                                        estadojaque = false;
                                        reyJaqueado = null;
                                    }
                                }
    
                            }
                            return true;
                        }
                    }
                }
    
            }
            return false;
        }else
        {
            return false;
        }
        
    }

    public boolean jaque(String colorRey, Tablero tab) {
        Pieza ReyEnemigo = tab.obtenerRey(colorRey);
        Pieza piezaAmenazante = tab.obtenerPiezaAmenazante(ReyEnemigo, ReyEnemigo.getColor());
        if(piezaAmenazante == null){
            return  false;
        }
        return  true;
    }
    public boolean jaqueMate(String colorRey, Tablero tab) {

        /*Posiciones donde se pueden mover las piezas aliadas al rey*/

        List<Posicion> listaPosicionColorRey= new ArrayList<>();
        /*REY*/
        Pieza ReyEnemigo = tab.obtenerRey(colorRey);
        /*Piezas aliadas al rey en JAQUE*/
        List<Pieza> piezasColorRey = Objects.equals(colorRey, "negro") ? tab.getPiezasNegras():tab.getPiezasBlancas();
        /*Pieza que amenaza al rey*/


        Pieza piezaAmenazante = tab.obtenerPiezaAmenazante(ReyEnemigo, ReyEnemigo.getColor());
        /*Termina el metodo, porq ninguna pieza amenaza al rey*/

        if(piezaAmenazante == null){
            return  false;
        }
        System.out.println(piezaAmenazante.getNombre());
        /* Agrega en una lista los movs de las piezas del color del rey y Excluye los movimientos del rey*/
        for (Pieza pieza : piezasColorRey) {
            if (pieza instanceof Rey) {
                continue;
            }
            List<Posicion> movimientosPosibles = pieza.obtenerMovimientosPosibles(tab.imprimirTablero());
            listaPosicionColorRey.addAll(movimientosPosibles);
        }



       List<Posicion> posicionCaminoRey =  obtenerPosicionesEnCamino(new Posicion(piezaAmenazante.getFila(), piezaAmenazante.getColumna()), new Posicion(ReyEnemigo.getFila(),ReyEnemigo.getColumna()));

        for (Posicion p:
             posicionCaminoRey) {
            System.out.println(p.getFila() + " "+ p.getColumna());
        }


        for (Pieza[] filaPieza : tab.imprimirTablero()) {
            for (Pieza pieza : filaPieza) {
                if (pieza != null && !(pieza instanceof Rey) && pieza.getColor().equals(colorRey)) {
                     List<Posicion> posicionesEliminar = new ArrayList<>();


                    for (Posicion p : pieza.getMovsPosibles()) {

                        boolean encontrado = false;

                        for (Posicion pos : posicionCaminoRey) {
                            if (pos.getFila() == p.getFila() && pos.getColumna() == p.getColumna()) {
                                encontrado = true;
                                break;
                            }
                        }
                        if (!encontrado) {
                            posicionesEliminar.add(p);
                        }

                    }
                    pieza.getMovsPosibles().removeAll(posicionesEliminar);
                }else if ((pieza instanceof Rey) && pieza.getColor().equals(colorRey))
                {
                    pieza.obtenerMovimientosPosibles(tablero.imprimirTablero());

                }
            }
        }




        for (Pieza[] filaPieza : tab.imprimirTablero()) {
            for (Pieza pieza : filaPieza) {
                if (pieza != null && Objects.equals(pieza.getColor(), colorRey))
                {
                    if(!pieza.getMovsPosibles().isEmpty())
                    {
                        return false;
                    }else if(!ReyEnemigo.getMovsPosibles().isEmpty())
                    {
                        return false;

                    }

                }
            }
        }

        return true;

    }


    public boolean verificarPosicionPieza(Pieza pieza) {
        if (pieza instanceof Peon) {
            if (Objects.equals(pieza.getColor(), "blanco") && pieza.getFila() == 0) {
                return true;
            } else if (Objects.equals(pieza.getColor(), "negro") && pieza.getFila() == 7) {
                return true;
            }
        }
        return false;
    }

    public List<Posicion> obtenerPosicionesEnCamino(Posicion piezaAmenazante, Posicion posicionRey) {
        List<Posicion> posicionesEnCamino = new ArrayList<>();

        int filaAmenazante = piezaAmenazante.getFila();
        int columnaAmenazante = piezaAmenazante.getColumna();
        int filaRey = posicionRey.getFila();
        int columnaRey = posicionRey.getColumna();

        int filaDif = Math.abs(filaRey - filaAmenazante);
        int columnaDif = Math.abs(columnaRey - columnaAmenazante);

        int filaStep = (filaAmenazante < filaRey) ? 1 : -1;
        int columnaStep = (columnaAmenazante < columnaRey) ? 1 : -1;

        int fila = filaAmenazante;
        int columna = columnaAmenazante;

        // Agregar la posición de la pieza amenazante
        posicionesEnCamino.add(new Posicion(fila, columna));

        // Agregar las posiciones intermedias en el camino hacia el rey
        while (fila != filaRey || columna != columnaRey) {
            if (fila != filaRey) {
                fila += filaStep;
            }
            if (columna != columnaRey) {
                columna += columnaStep;
            }
            posicionesEnCamino.add(new Posicion(fila, columna));
        }

        return posicionesEnCamino;
    }

    public java.util.Set<Movimiento> obtenerMovimientos()
    {
        return listaMovimientos;
    }
    public void empatar(int i){

        if(i == 1)
        {
            enCurso = false;

        }
    
    }
    public boolean hayJaque(){
        return estadojaque;
    }
    public boolean hayJaqueMate(){
        return estadoJaqueMate;
    }
    public Pieza piezaJaqueada(){
        return reyJaqueado;
    }
    public boolean turnoDe(){
        return  turnoBlanco;
    }
    public String obtenerNameJugadorBlanco(){
        return jBlanco ;
    }
    public String obtenerNameJugadorNegro(){
        return jNegro ;
    }
    public boolean enCurso(){
        return  enCurso;
    }
    public void CargarTablero()
    {
        tablero.cargarTablero();
    }
}