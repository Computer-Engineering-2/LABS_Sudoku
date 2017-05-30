package Domini;

public class Coordenada {

	private int fila;
	private int columna;

	public Coordenada(int x, int y) {
		this.fila = x;
		this.columna = y;
	}

	public boolean foraTauler() {
		return (fila > 8 || columna > 8 || fila <0 || columna < 0);
	}

	public Coordenada[] retornaFila() throws Exception {
		Coordenada coordenades[] = new Coordenada[9];
		for (int i = 0; i < 9; i++)
			coordenades[i] = new Coordenada(fila, i);
		return coordenades;
	}

	public Coordenada[] retornaColumna() throws Exception {
		Coordenada coordenades[] = new Coordenada[9];
		for (int i = 0; i < 9; i++)
			coordenades[i] = new Coordenada(i, columna);
		return coordenades;
	}

	public Coordenada[] retornaRegio() throws Exception {

		Coordenada coordenades[] = new Coordenada[8];
		int filaRegio;
		int columnaRegio;

		// Obtenir primera fila de la regió que pertany
		if (fila >= 0 && fila <= 2)
			filaRegio = 0;
		else if (fila >= 3 && fila <= 5)
			filaRegio = 3;
		else
			filaRegio = 6;

		// Primera columna de la regió que pertany
		if (columna >= 0 && columna <= 2)
			columnaRegio = 0;
		else if (columna >= 3 && columna <= 5)
			columnaRegio = 3;
		else
			columnaRegio = 6;

		// Obtenir coordenades a partir de pivot (filaRegio i columnaRegio)
		int columnaCoordenades = 0;
		for (int f = filaRegio; f <= filaRegio + 2; f++)
			for (int c = columnaRegio; c <= columnaRegio + 2; c++)
				if (!(f== fila && c == columna)){
					coordenades[columnaCoordenades]= new Coordenada(f, c);
					columnaCoordenades++;
				}
		return coordenades;
	}

	public int getFila() {
		return fila;
	}
	public int getColumna() {
		return columna;
	}
}