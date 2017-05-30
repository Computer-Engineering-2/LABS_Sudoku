package Domini;

public class Casella {

	private final static int DEFECTE = 0;

	private int valor;
	private boolean editable;

	public Casella() {
		this.valor = DEFECTE;
		this.editable = true;
	}

	public int getValor() {
		return this.valor;
	}

	public boolean getEditable() {
		return this.editable;
	}

	public boolean esBuit() {
		if (this.valor == DEFECTE)
			return true;
		else
			return false;
	}

	public void setValor(int valor) throws Exception {
		validar(valor);
		validarEditable();
		this.valor = valor;
	}

	private void validarEditable() throws Exception {
		if (!editable) {
			throw new Exception("ERROR: Casella inicial, no es pot canviar.");
		}
	}

	private void validar(int valor) throws Exception {
		if (valor < 1 || valor > 9) {
			throw new Exception("ERROR: El número " + valor + " no és correcte, ha de ser 1..9.");
		}
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public static int getPosBuida() {
		return DEFECTE;
	}

	public void buidar() throws Exception {
		validarEditable();
		valor = DEFECTE;
	}

}