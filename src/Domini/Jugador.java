package Domini;

public class Jugador {
	private String nom;
	private boolean estaJugant;
	
	public Jugador (String nom, boolean estaJugant){
		this.nom = nom;
		this.estaJugant = estaJugant;
	}
	
	public Jugador(String nom){
		this.nom = nom;
		this.estaJugant = false;
	}
	    
	public boolean getEstaJugant() {
		return this.estaJugant;
	}

	public void setEstaJugant(boolean newEJ) {
		this.estaJugant = newEJ;
	}    

	public String getNom() {
	    return nom;
	}

	public void setNom(String nom) {
	    this.nom = nom;
	}
}
