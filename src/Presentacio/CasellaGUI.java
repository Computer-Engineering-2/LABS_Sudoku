package Presentacio;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class CasellaGUI extends JTextField {

	private int fila, columna;

	public CasellaGUI(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
		this.setText("");
		Font fuente = new Font("Calibri", 1, 25);
		this.setFont(fuente);
		this.setHorizontalAlignment(SwingConstants.CENTER);

		if (fila == 2 || fila == 5)
			if (columna == 2 || columna == 5)
				this.setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, Color.BLACK));
			else
				this.setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, Color.BLACK));
		else if (columna == 2 || columna == 5)
			this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, Color.BLACK));
		else
			this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	}

	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

	public void casellaInicial() {
		this.setForeground(Color.YELLOW);
		this.setBackground(Color.gray);
		this.setEditable(false);
	}

	public void borrarVermell() {
		if (this.isEditable())
			this.setBackground(Color.WHITE);
		else
			this.setBackground(Color.GRAY);
	}
	
	public void esborrar(){
		this.setText("");
		this.setBackground(Color.WHITE);
		this.setForeground(Color.BLACK);
	}
}
