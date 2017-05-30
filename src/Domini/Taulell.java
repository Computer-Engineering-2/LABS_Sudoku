package Domini;

import Aplicacio.AbsGraella;

public class Taulell {

	private Casella[][] taulell;
	private static final int MIDA = 9;
	private Coordenada coordenada;

	public Taulell() throws Exception {
		this.taulell = new Casella[MIDA][MIDA];

		for (int i = 0; i < MIDA; i++)
			for (int j = 0; j < MIDA; j++)
				taulell[i][j] = new Casella();
	}

	public void generarEquivalent() throws Exception {
		eliminarNoInicials();
		GraellaEquivalent equivalent = new GraellaEquivalent(taulell);
		equivalent.generarEquivalent();
	}
	
	private void eliminarNoInicials() throws Exception{
		for(int i = 0; i < MIDA; i++)
			for(int j = 0; j < MIDA; j++)
				if(taulell[i][j].getEditable())
					taulell[i][j].buidar();
	}

	public int[] realitzarJugada(String filaS, String columnaS, String valorS) throws Exception {

		if (!valorS.matches("[+-]?\\d*(\\.\\d+)?")) //comproba si es numero
			throw new Exception("ERROR : El caràcter introduit no és correcte, ha de ser 1..9.");

		int valor = Integer.parseInt(valorS);
		
		if (valor == 0) {
			esborrarValor(filaS, columnaS);
			throw new Exception("ERROR : El caràcter introduit no és correcte, ha de ser 1..9.");
		} else {

			int fila = Integer.parseInt(filaS);
			int columna = Integer.parseInt(columnaS);

			int[] coordenadaError = null;
			coordenada = new Coordenada(fila, columna);

			if (esInicial(fila, columna))
				throw new Exception("ERROR: Aquesta casella no es pot modificar, és incial.");

			if (valor == Casella.getPosBuida()) {
				this.taulell[fila][columna].buidar();
			} else {

				Coordenada[] contingut = coordenada.retornaRegio();
				coordenadaError = existeixValor(contingut, valor);

				if (coordenadaError == null) {
					contingut = coordenada.retornaFila();
					coordenadaError = existeixValor(contingut, valor);

					if (coordenadaError == null) {
						contingut = coordenada.retornaColumna();
						coordenadaError = existeixValor(contingut, valor);
					}
				}

				// Afegir nou valor
				this.taulell[fila][columna].setValor(valor);
			}

			return coordenadaError;
		}

	}

	public void esborrarValor(String filaS, String columnaS) throws Exception {

		int fila = Integer.parseInt(filaS);
		int columna = Integer.parseInt(columnaS);

		if (esInicial(fila, columna))
			throw new Exception("ERROR: Aquesta casella no es pot modificar, és incial.");
		else
			this.taulell[fila][columna].buidar();
	}

	public void realitzarJugada(int fila, int columna, int valor, boolean editable) throws Exception {
		realitzarJugada(String.valueOf(fila), String.valueOf(columna), String.valueOf(valor));
		this.taulell[fila][columna].setEditable(editable);
	}

	public boolean esInicial(int fila, int columna) {
		return !taulell[fila][columna].getEditable();
	}

	public void omplir() throws Exception {
		AbsGraella.graella(this);
	}

	public int[] existeixValor(Coordenada[] coordenades, int valor) throws Exception {
		int[] coordenadaError = null;
		for (int y = 0; y < coordenades.length; y++)
			if (this.taulell[coordenades[y].getFila()][coordenades[y].getColumna()].getValor() == valor) {
				coordenadaError = new int[2];
				coordenadaError[0] = coordenades[y].getFila();
				coordenadaError[1] = coordenades[y].getColumna();
			}
		return coordenadaError;
	}

	public boolean taulaEmplenada() {
		boolean plena = true;
		for (int x = 0; x < 9 /* && plena */; x++)
			for (int y = 0; y < 9 /* && plena */; y++)
				if (this.taulell[x][y].getValor() == 0)
					plena = false;
		return plena;
	}

	public int[][] returnIntTaulell() {
		int matriuTaulell[][] = new int[9][9];
		for (int i = 0; i < this.taulell.length; i++)
			for (int j = 0; j < this.taulell.length; j++)
				if (!this.taulell[i][j].esBuit())
					matriuTaulell[i][j] = this.taulell[i][j].getValor();
				else
					matriuTaulell[i][j] = 0;
		return matriuTaulell;
	}

	private int quantesNoBuides(){
		int cont=0;
		for (int i = 0; i < this.taulell.length; i++)
			for (int j = 0; j < this.taulell.length; j++)			
				if(!taulell[i][j].esBuit())
					cont++;
		return cont;
	}
	
	public void maximCreacio() throws Exception{
		if (quantesNoBuides() == 80)
			throw new Exception ("ERROR: El màxim de pistes permés son 80.");
	}

	public void ferInicials() throws Exception {		
		if(quantesNoBuides()<17) throw new Exception("ERROR: Es necessita un mínim de 17 caselles.");		
		for (int i = 0; i < this.taulell.length; i++)
			for (int j = 0; j < this.taulell.length; j++)			
				if(!taulell[i][j].esBuit())
					taulell[i][j].setEditable(false);	
	}
}