package Aplicacio;

import Domini.Taulell;
import Domini.Partida;
import Domini.Jugador;
import Persistencia.JugadorBBDD;
import Persistencia.PartidaBBDD;
import java.util.List;

public class Controlador {

	Taulell taulell;
	Jugador jugador;
	public Controlador() throws Exception {
		taulell = new Taulell();	
		
	}
	
	public Jugador obtenirJugador(String nomJugador) throws Exception {
		JugadorBBDD jugadorBD = new Persistencia.JugadorBBDD();
		return jugadorBD.trobarJugador(nomJugador);
	}
	
	public List<Object[]> obtenirPartides(Jugador jug) throws Exception {
		PartidaBBDD partidaBD = new PartidaBBDD();
		return partidaBD.descripcioPartides(jug);
	}

	public void guardarPartida(String nomJugador, Partida joc) throws Exception {
		PartidaBBDD pBD = new PartidaBBDD();
		pBD.guardarPartida(nomJugador, joc);
	}
	
	public static Partida obtenirPartida(int partidaId) throws Exception {
		PartidaBBDD pBD = new PartidaBBDD();
		return pBD.obtenirPartida(partidaId);
	}
	public Jugador crearJugador(String nomJugador)throws Exception{
		JugadorBBDD jugadorBD = new Persistencia.JugadorBBDD();
		Jugador jugador = new Jugador(nomJugador,true);
		jugadorBD.guardarJugador(jugador);
		return jugador;
	}

	
	public Jugador carregarJugador(String nomJugador)throws Exception{
		JugadorBBDD jugadorBD = new Persistencia.JugadorBBDD();
		this.jugador = jugadorBD.getJugadorBBDD(nomJugador);
		jugador.setEstaJugant(true);
		jugadorBD.guardarJugador(jugador);
		return jugador;
	}
	
	
	public void guardarJugador(Jugador jugador)throws Exception{
		JugadorBBDD jugadorBD = new Persistencia.JugadorBBDD();
		jugador.setEstaJugant(false);
		jugadorBD.guardarJugador(jugador);
	}
	
	public static void eliminarPartida(int partidaId) throws Exception {
		new PartidaBBDD().eliminarPartida(partidaId);
	}
	
	public int[] realitzarJugada(String filaS, String columnaS, String valorS) throws Exception {
		return taulell.realitzarJugada(filaS, columnaS, valorS);
	}
	
	public void realitzarJugada(String filaS, String columnaS) throws Exception {
		taulell.esborrarValor(filaS,columnaS);
	}

	public boolean isFinalitzar() {
		return taulell.taulaEmplenada();
	}
	
	public void generarEquivalent() throws Exception{
		taulell.generarEquivalent();
	}

	public int[][] entersTaulell() throws Exception {		
		return taulell.returnIntTaulell();
	}
	
	public void omplirTaulellInicial() throws Exception{
		taulell.omplir();
	}
	
	public boolean esInicial(int fila, int columna){
		return taulell.esInicial(fila, columna);
	}

	public void ferInicials() throws Exception {
		taulell.ferInicials();		
	}
	
	public void maximCreacio() throws Exception{
		taulell.maximCreacio();
	}

}