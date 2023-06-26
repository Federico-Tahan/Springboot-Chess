package com.example.Ajedrez;

import com.example.Ajedrez.dominio.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AjedrezApplicationTests {
	@Test
	public void ValidarGetPieza(){

		Tablero tablero = Mockito.mock(Tablero.class);
		Mockito.when(tablero.getPieza(0, 1)).thenReturn(new Caballo("negro", 0, 1,"Caballo"));
		Mockito.when(tablero.getPieza(6, 4)).thenReturn(new Peon("blanco", 6, 4, "Peon"));

		Caballo caballoEsperado = new Caballo("negro", 0, 1,"Caballo");

		Pieza piezaObtenida1 = tablero.getPieza(0, 1);
		Pieza piezaObtenida2 = tablero.getPieza(6, 4);

		Assertions.assertEquals(caballoEsperado.getNombre(), piezaObtenida1.getNombre());
		Assertions.assertNotEquals(caballoEsperado.getNombre(), piezaObtenida2.getNombre());

	}
	@Test
	public void ValidarObtenerRey() {
		Tablero tablero = Mockito.spy(new Tablero());
		tablero.inicializarTablero();
		Rey rey = Mockito.mock(Rey.class);
		Mockito.when(tablero.obtenerRey("blanco")).thenReturn(rey);
		Mockito.when(tablero.obtenerRey("negro")).thenReturn(rey);

		Assertions.assertEquals(rey.getNombre(), tablero.obtenerRey("blanco").getNombre());
		Assertions.assertEquals(rey.getNombre(), tablero.obtenerRey("negro").getNombre());
		Assertions.assertNull(rey.getColor(), tablero.obtenerRey("negro").getColor());
	}
	@Test
	public void validarEsPosicionValida() {
		Tablero tablero = Mockito.mock(Tablero.class);

		Mockito.when(tablero.esPosicionValida(0, 1)).thenReturn(true);
		Mockito.when(tablero.esPosicionValida(0, 2)).thenReturn(true);
		Mockito.when(tablero.esPosicionValida(0, 3)).thenReturn(true);
		Mockito.when(tablero.esPosicionValida(1, 4)).thenReturn(true);
		Mockito.when(tablero.esPosicionValida(4, 4)).thenReturn(true);

		Mockito.when(tablero.esPosicionValida(9, 1)).thenReturn(false);
		Mockito.when(tablero.esPosicionValida(0, 9)).thenReturn(false);
		Mockito.when(tablero.esPosicionValida(4, 10)).thenReturn(false);
		Mockito.when(tablero.esPosicionValida(3, 12)).thenReturn(false);
		Mockito.when(tablero.esPosicionValida(9, 0)).thenReturn(false);

		assertTrue(tablero.esPosicionValida(0, 1));
		assertTrue(tablero.esPosicionValida(0, 2));
		assertTrue(tablero.esPosicionValida(0, 3));
		assertTrue(tablero.esPosicionValida(1, 4));
		assertTrue(tablero.esPosicionValida(4, 4));

		Assertions.assertFalse(tablero.esPosicionValida(9, 1));
		Assertions.assertFalse(tablero.esPosicionValida(0, 9));
		Assertions.assertFalse(tablero.esPosicionValida(4, 10));
		Assertions.assertFalse(tablero.esPosicionValida(3, 12));
		Assertions.assertFalse(tablero.esPosicionValida(9, 0));
	}
	@Test
	public void ValidarObtenerPiezaAmenazante()
	{
		Tablero t = new Tablero();
		Rey r = new Rey("blanco",0,0,"Rey");
		t.setPieza(0,0,r);
		Reina reina = new Reina("negro",7,0,"Reina");
		t.setPieza(7,0,reina);
		Peon peon = new Peon("negro",4,0,"Peon");
		t.setPieza(7,0,reina);

		Assertions.assertEquals(reina,t.obtenerPiezaAmenazante(r,r.getColor()));
		Assertions.assertNotEquals(peon,t.obtenerPiezaAmenazante(r,r.getColor()));

	}
	@Test
	public void ValidarGetPiezasBlancas(){
		Tablero t = new Tablero();

		Reina reina  = new Reina("blanco",0,0,"Reina");
		Alfil alfil  = new Alfil("blanco",0,2,"Alfin");
		Peon peon  = new Peon("blanco",0,3,"Peon");

		t.setPieza(0,0,reina);
		t.setPieza(0,2,alfil);
		t.setPieza(0,3,peon);


		List<Pieza> piezasBlancas = new ArrayList<>();
		piezasBlancas.add(reina);
		piezasBlancas.add(alfil);
		piezasBlancas.add(peon);

		Assertions.assertEquals(piezasBlancas,t.getPiezasBlancas());

		piezasBlancas.add(peon);

		Assertions.assertNotEquals(piezasBlancas,t.getPiezasBlancas());

	}
	@Test
	public void ValidarGetPiezasNegras(){
		Tablero t = new Tablero();

		Reina reina  = new Reina("negro",0,0,"Reina");
		Alfil alfil  = new Alfil("negro",0,2,"Alfin");
		Peon peon  = new Peon("negro",0,3,"Peon");

		t.setPieza(0,0,reina);
		t.setPieza(0,2,alfil);
		t.setPieza(0,3,peon);


		List<Pieza> piezasNegras = new ArrayList<>();
		piezasNegras.add(reina);
		piezasNegras.add(alfil);
		piezasNegras.add(peon);

		Assertions.assertEquals(piezasNegras,t.getPiezasNegras());

		piezasNegras.add(peon);

		Assertions.assertNotEquals(piezasNegras,t.getPiezasNegras());

	}
	@Test
	public void ValidarImprimirTablero(){
		Tablero t = new Tablero();
		Reina reina  = new Reina("negro",0,0,"Reina");
		Alfil alfil  = new Alfil("negro",0,2,"Alfin");
		Peon peon  = new Peon("negro",0,3,"Peon");

		t.setPieza(0,0,reina);
		t.setPieza(0,2,alfil);
		t.setPieza(0,3,peon);

		Reina reina1  = new Reina("blanco",7,0,"Reina");
		Alfil alfil1  = new Alfil("blanco",7,2,"Alfin");
		Peon peon1 = new Peon("blanco",7,3,"Peon");

		t.setPieza(7,0,reina1);
		t.setPieza(7,2,alfil1);
		t.setPieza(7,3,peon1);

		Pieza[][] tab = new Pieza[8][8];

		tab[0][0] = reina;
		tab[0][2] = alfil;
		tab[0][3] = peon;
		tab[7][0] = reina1;
		tab[7][2] = alfil1;
		tab[7][3] = peon1;

		Assertions.assertArrayEquals(tab,t.imprimirTablero());
		tab[7][4] = peon1;
		Assertions.assertNotEquals(tab,t.imprimirTablero());

	}
	@Test
	public void testValidarMovimiento() {
		Juego juego = new Juego();

		Tablero tablero = new Tablero();
		tablero.inicializarTablero();
		juego.setTablero(tablero);

		boolean turnoBlanco = true;
		juego.setTurnoBlanco(turnoBlanco);

		boolean resultado = juego.validarMovimiento(6, 3, 5, 3);

		assertTrue(juego.validarMovimiento(7, 1, 5, 0));
		Assertions.assertFalse(juego.validarMovimiento(7, 2, 5, 0));

		assertTrue(resultado);
	}
	@Test
	public void testMoverPieza_MovimientoValidoyJaque() {
		Juego j = new Juego();
		Tablero t = new Tablero();
		j.setTablero(t);
		t.inicializarTablero();

		GameStatus gm = new GameStatus();

		GameStatus result = j.moverPieza(6, 4, 5, 4, gm);

		assertTrue(result.isMovAprobado());

		GameStatus result1 = j.moverPieza(1, 5, 3, 5, gm);
		assertTrue(result1.isMovAprobado());

		GameStatus result2 = j.moverPieza(6, 1, 5, 1, gm);
		assertTrue(result2.isMovAprobado());

		GameStatus result3 = j.moverPieza(1, 6, 3, 6, gm);
		assertTrue(result3.isMovAprobado());

		GameStatus result8 = j.moverPieza(2, 6, 3, 6, gm);
		Assertions.assertFalse(result8.isMovAprobado());

		GameStatus result5 = j.moverPieza(7, 3, 3, 7, gm);
		assertTrue(result5.isMovAprobado());

		assertTrue(result5.isJaqueMate());
		Rey r = new Rey("negro",0,4,"Rey");
		assertEquals(r.getFila(),result5.getReyJaqueado().getFila());

		assertEquals(result5.getReyJaqueado(),j.piezaJaqueada());


	}
	@Test
	public void testValidarMovimientos(){
		Juego juego= new Juego();
		Tablero t= new Tablero();
		juego.setTablero(t);
		t.inicializarTablero();

		assertTrue(juego.validarMovimiento(6,1,5,1));
		assertTrue(juego.validarMovimiento(7,1,5,0));
		Assertions.assertFalse(juego.validarMovimiento(3,1,4,7));


	}
	@Test
	public void ValidarEmpate(){
		Juego j = new Juego();
		j.empatar(0);
		assertTrue(j.enCurso());
		j.empatar(1);
		Assertions.assertFalse(j.enCurso());

	}
	@Test
	public void ValidarPosicionPieza(){
		Juego j= new Juego();
		Tablero t= new Tablero();
		j.setTablero(t);
		Peon peon= new Peon("blanco",0,0,"Peon");
		Rey rey = new Rey("negro",7,7,"Rey");
		t.setPieza(0,0,peon);
		t.setPieza(7,7,rey);
		assertTrue(j.verificarPosicionPieza(peon));
		Assertions.assertFalse(j.verificarPosicionPieza(rey));
	}
	@Test
	public void ValidarJacke(){
		Juego j = new Juego();
		Tablero t = new Tablero();
		j.setTablero(t);
		t.inicializarTablero();

		GameStatus gm = new GameStatus();

		GameStatus result = j.moverPieza(6, 4, 5, 4, gm);

		assertTrue(result.isMovAprobado());

		GameStatus result1 = j.moverPieza(1, 5, 3, 5, gm);
		assertTrue(result1.isMovAprobado());

		GameStatus result5 = j.moverPieza(7, 3, 3, 7, gm);
		assertTrue(result5.isMovAprobado());

		assertTrue(j.jaque("negro",t));
	}
	@Test
	public  void ValidarSetEmpate()
	{
		Juego j = new Juego();
		j.setEmpataron(true);
		assertTrue(j.isEmpataron());
		j.setEmpataron(false);
		Assertions.assertFalse(j.isEmpataron());

	}
	@Test
	public void ValidarTraerTablero(){
		Juego j = new Juego();

		Pieza[][] p = new Pieza[8][8];
		assertArrayEquals(p,j.traerTablero());
	}
	@Test
	public void ValidarCargarTablero(){

		Juego j = new Juego();
		j.iniciarJuego();
		j.CargarTablero();
		assertTrue(j.traerTablero()[1][0] instanceof Peon);

	}
	@Test
	public void ValidarJuego(){

		Juego j = new Juego();

		Set<Movimiento> lm= new HashSet<Movimiento>();

		j.setListaMovimientos(lm);
		assertEquals(lm,j.obtenerMovimientos());

		j.setJBlanco("Fede");
		j.setJNegro("Luis");

		j.setEstadojaque(true);
		j.setEstadoJaqueMate(true);
		Rey r = new Rey("negro",0,1,"Rey");

		j.setReyJaqueado(r);

		assertTrue(j.isEstadojaque());
		assertTrue(j.isEstadoJaqueMate());
		assertTrue(j.hayJaque());
		assertTrue(j.hayJaqueMate());
		assertEquals(j.getJBlanco(),"Fede");
		assertEquals(j.getJNegro(),"Luis");
		assertEquals(j.obtenerNameJugadorBlanco(),"Fede");
		assertEquals(j.obtenerNameJugadorNegro(),"Luis");
		assertEquals(r,j.getReyJaqueado());

		j.setId(1L);
		assertEquals(j.getId(),1L);

		assertTrue(j.turnoDe());
		assertTrue(j.isTurnoBlanco());

	}
	@Test
	public void ValidarPieza(){

		Peon p = new Peon();
		p.setColor("negro");
		p.setId(1L);
		p.setNombre("Peon");
		List<Posicion> plist = new ArrayList<>();

		p.setMovsPosibles(plist);


		assertEquals(p.getColor(),"negro");
		assertEquals(p.getId(),1L);
		assertEquals(p.getNombre(),"Peon");
		assertEquals(p.getMovsPosibles(),plist);


	}
	@Test
	public void ValidarCambiarFicha(){
		Juego j = new Juego();
		Tablero t= new Tablero();
		j.setTablero(t);
		GameStatus gm= new GameStatus();

		Peon peon= new Peon("blanco",0,0,"Peon");
		Rey rey = new Rey("negro",7,7,"Rey");
		t.setPieza(0,0,peon);
		t.setPieza(7,7,rey);
		assertTrue(j.cambiarFicha(1,gm));
	}
	@Test
	public void testObtenerPosicionesEnCamino() {
		Juego j = new Juego();

		Posicion piezaAmenazante = new Posicion(0, 0);
		Posicion posicionRey = new Posicion(3, 0);

		List<Posicion> posicionesEnCamino = j.obtenerPosicionesEnCamino(piezaAmenazante, posicionRey);

		Assertions.assertEquals(4, posicionesEnCamino.size());
		Assertions.assertEquals(0, posicionesEnCamino.get(0).getFila());
		Assertions.assertEquals(0, posicionesEnCamino.get(0).getColumna());
		Assertions.assertEquals(1, posicionesEnCamino.get(1).getFila());
		Assertions.assertEquals(0, posicionesEnCamino.get(1).getColumna());
		Assertions.assertEquals(2, posicionesEnCamino.get(2).getFila());
		Assertions.assertEquals(0, posicionesEnCamino.get(2).getColumna());
		Assertions.assertEquals(3, posicionesEnCamino.get(3).getFila());
		Assertions.assertEquals(0, posicionesEnCamino.get(3).getColumna());
	}
	@Test
	public  void ValidarisEnCurso()
	{
		Juego j = new Juego();

		j.iniciarJuego();

		assertTrue(j.isEnCurso());

		GameStatus gm = new GameStatus();
		GameStatus result = j.moverPieza(6, 4, 5, 4, gm);
		GameStatus result1 = j.moverPieza(1, 5, 3, 5, gm);
		GameStatus result2 = j.moverPieza(6, 1, 5, 1, gm);
		GameStatus result3 = j.moverPieza(1, 6, 3, 6, gm);
		GameStatus result8 = j.moverPieza(2, 6, 3, 6, gm);
		GameStatus result5 = j.moverPieza(7, 3, 3, 7, gm);
		Assertions.assertFalse(j.isEnCurso());



		j.setEnCurso(true);
		assertTrue(j.isEnCurso());

	}
	@Test
	public void ValidarCambiarFichaJaque(){
		Juego j = new Juego();
		Tablero t= new Tablero();
		j.setTablero(t);
		GameStatus gm= new GameStatus();


		Peon peon= new Peon("blanco",0,0,"Peon");
		Rey rey = new Rey("negro",7,7,"Rey");
		t.setPieza(0,0,peon);
		t.setPieza(0,7,rey);
		assertTrue(j.cambiarFicha(4,gm));
		t.setPieza(0,0,peon);

		assertTrue(j.cambiarFicha(1,gm));
		t.setPieza(0,0,peon);

		assertTrue(j.cambiarFicha(2,gm));
		t.setPieza(0,0,peon);

		assertTrue(j.cambiarFicha(3,gm));


		Peon peonN= new Peon("negro",0,0,"Peon");
		Rey reyB = new Rey("blanco",7,7,"Rey");
		t.setPieza(0,0,peonN);
		t.setPieza(0,7,reyB);
		assertTrue(j.cambiarFicha(4,gm));
		t.setPieza(0,0,peonN);

		assertTrue(j.cambiarFicha(1,gm));
		t.setPieza(0,0,peonN);

		assertTrue(j.cambiarFicha(2,gm));
		t.setPieza(0,0,peonN);

		assertTrue(j.cambiarFicha(3,gm));

		t.setPieza(0,0,peonN);



	}
	@Test
	public void ValidarGameStatus(){

		GameStatus gm = new GameStatus();

		gm.setNameJugadorNegro("luis");
		gm.setNameJugadorBlanco("luis");
		gm.setEnCurso(true);
		gm.setTurnoBlanco(true);
		gm.setJaque(true);
		gm.setGanador("luis");


		assertEquals(gm.getNameJugadorBlanco(),"luis");
		assertEquals(gm.getNameJugadorNegro(),"luis");
		assertTrue(gm.isEnCurso());
		assertTrue(gm.isTurnoBlanco());
		assertTrue(gm.isJaque());
		assertEquals(gm.getGanador(),"luis");

		Juego j = new Juego();

		Tablero t = new Tablero();

		Pieza p = new Peon("blanco",1,0,"Peon");
		t.setPieza(1,0,p);

		j.setTablero(t);

		j.moverPieza(1,0,0,0,gm);

		assertTrue(gm.isCoronarPeon());
	}

	@Test
	public void ValidarPosicion(){

		Posicion p = new Posicion();

		p.setId(2L);

		assertEquals(p.getId(),2L);

		Pieza peon = new Peon("negro",0,1,"Peon");
		p.setPieza(peon);

		p.setColumna(1);
		p.setFila(0);

		assertEquals(p.getColumna(),1);
		assertEquals(p.getFila(),0);

		assertEquals(peon,p.getPieza());
	}
	@Test
	public void ValidarMovimiento(){

		Movimiento m = new Movimiento();

		m.setId(1L);

		assertEquals(m.getId(),1L);

		m.setMovDestinoCol(1);
		m.setMovDestinoFila(1);
		m.setMovOrigenCol(2);
		m.setMovOrigenFila(2);

		assertEquals(m.getMovDestinoCol(),1);
		assertEquals(m.getMovDestinoFila(),1);
		assertEquals(m.getMovOrigenCol(),2);
		assertEquals(m.getMovOrigenFila(),2);
		assertEquals(m.getMovDestinoCol(),1);
		assertEquals(m.getId(),1L);
	}


}
