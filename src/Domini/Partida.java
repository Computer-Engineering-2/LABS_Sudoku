package Domini;

import java.sql.Timestamp;

import Domini.Taulell;

public class Partida {

	public final static int STATUS_NORMAL = 0;
	public final static int STATUS_ACABAT = 1;


    private int id;					// identificador únic de partida
    private Timestamp data;			// Data guardada
	private Taulell taulell;		// Taulell de joc
	private int status;				// Estat del joc 
	private String statusDescription;	// Descripció de l'estat del joc 

	
	public Partida() throws Exception {
		this.id = 0;
		this.data = null;
		this.taulell = new Taulell();
		this.status = STATUS_NORMAL;
		this.statusDescription = "Joc preparat";
	}
	
	
	public Partida(int partidaId) {
		this.id = partidaId;
	}

	public int getId() {
		return this.id;
	}
	public void setId(int value) {
		this.id = value;
	}
	
	public void setData(Timestamp data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}
	public String getStatusDescripcio() {
		return this.statusDescription;
	}
	
	

}
