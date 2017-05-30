package Domini;

import java.util.Random;

public class GraellaEquivalent {

	Random rnd = new Random();
	Casella[][] taulell;

	public GraellaEquivalent(Casella[][] taulell) {
		this.taulell = taulell;
	}

	private int[] parellesRegio(int total) {
		int[] parelles = { aleatoriEntre(0, total), 0 };
		if (parelles[0] >= 0 && parelles[0] <= 2) {
			parelles[1] = aleatoriEntre(0, 2);
			while (parelles[0] == parelles[1])
				parelles[1] = aleatoriEntre(0, 2);
		} else if (parelles[0] >= 3 && parelles[0] <= 5) {
			parelles[1] = aleatoriEntre(3, 5);
			while ((parelles[0] == parelles[1]) && (parelles[1] >= 3 && parelles[1] <= 5))
				parelles[1] = aleatoriEntre(3, 5);
		} else if (parelles[0] >= 6 && parelles[0] <= 8) {
			parelles[1] = aleatoriEntre(6, 8);
			while ((parelles[0] == parelles[1]) && (parelles[1] >= 6 && parelles[1] <= 8))
				parelles[1] = (aleatoriEntre(6, 8));
		}
		return parelles;
	}

	public void generarEquivalent() throws Exception {
		int nCanvis = (int) (rnd.nextDouble() * 6 + 1), aFer = 0;
		boolean[] fets = { false, false, false, false, false, false };
		System.out.println("\nNum Canvis: " + nCanvis);
		while (nCanvis != 0) {
			aFer = (int) (rnd.nextDouble() * 6 + 1); // QUIN CANVI FER aleatori
			if (!fets[aFer - 1]) {
				switch (aFer) {
				case 1:
					intercanviColumnesFiles(true);
					fets[0] = true;
					break;
				case 2:
					intercanviColumnesFiles(false);
					fets[1] = true;
					break;
				case 3:
					girarDreta();
					fets[2] = true;
					break;
				case 4:
					transposar();
					fets[3] = true;
					break;
				case 5:
					System.out.print("Intercanvi Regions horitzontals, ");
					intercanviRegions(true); // TRUE horizontalment
					fets[4] = true;
					break;
				case 6:
					System.out.print("Intercanvi Regions verticals, ");
					intercanviRegions(false); // FALSE verticalment
					fets[5] = true;
					break;
				}
				nCanvis--;
			}
		}
	}

	private void intercanviColumnesFiles(boolean filaCol) throws Exception {
		String filaColumna;
		if (!filaCol)
			filaColumna = "columna";
		else
			filaColumna = "fila";
		int[] parella = parellesRegio(8);
		System.out.println("Canvi de " + filaColumna + ", origen: " + parella[0] + ", destí: " + parella[1]);
		intercanviarColumnesFiles(parella, filaCol);
	}

	private void intercanviarColumnesFiles(int parella[], boolean filaCol) throws Exception {
		Casella[] aux = new Casella[9];
		if (!filaCol) {
			for (int fila = 0; fila < 9; fila++) {
				aux[fila] = taulell[fila][parella[0]];
				taulell[fila][parella[0]] = taulell[fila][parella[1]];
				taulell[fila][parella[1]] = aux[fila];
			}
		} else {
			for (int col = 0; col < 9; col++) {
				aux[col] = taulell[parella[0]][col];
				taulell[parella[0]][col] = taulell[parella[1]][col];
				taulell[parella[1]][col] = aux[col];
			}
		}
	}

	private void copiarTaulell(Casella[][] altre) {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				taulell[i][j] = altre[i][j];
	}

	private void girarDreta() {
		int nGirs = (int) (rnd.nextDouble() * 3 + 1);
		System.out.println("Girar (1=90º, 2=180º, 3=270º): " + nGirs);
		while (nGirs != 0) {
			Casella[][] aux = new Casella[9][9];
			for (int i = 0; i < 9; i++)
				for (int j = 0; j < 9; j++)
					aux[j][9 - 1 - i] = taulell[i][j];
			copiarTaulell(aux);
			nGirs--;
		}
	}

	private void transposar() {
		System.out.println("Trasposta");
		Casella[][] aux = new Casella[9][9];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				aux[j][i] = taulell[i][j];
		copiarTaulell(aux);
	}

	private void intercanviRegions(boolean horizontalOVertical) throws Exception {

		int[] regions = indexRegio();
		for (int i = regions[0]; i < regions[0] + 3; i++) {
			int[] aIntercanviar = { i, regions[1] };
			intercanviarColumnesFiles(aIntercanviar, horizontalOVertical);
			System.out.print(i);
			regions[1]++;
		}
		System.out.println(" per " + (regions[1] - 3) + (regions[1] - 2) + (regions[1] - 1));
	}

	private int[] indexRegio() {
		int[] regions = parellesRegio(2);
		for (int i = 0; i < 2; i++)
			if (regions[i] == 1)
				regions[i] = 3;
			else if (regions[i] == 2)
				regions[i] = 6;
		return regions;
	}

	private int aleatoriEntre(int x, int y) {
		return (int) Math.floor(Math.random() * (y - x + 1) + x);
	}

}