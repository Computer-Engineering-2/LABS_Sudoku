package Presentacio;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import Aplicacio.Controlador;

import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.JButton;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class SudokuGUI extends JFrame {
	
	private Controlador controlador = new Controlador();
	private final int MIDA = 9;
	private CasellaGUI[][] casella = new CasellaGUI[MIDA][MIDA];
	private JPanel taulellGUI = new JPanel();
	static boolean iniciarBuit = false;
	private void incialitzacio() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(450, 450);
		this.setResizable(false);
		this.setTitle("SUDOKU");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(".\\src\\Presentacio\\sudokuPng.png"));
	}


	
	SudokuGUI() throws Exception {
		contructorEstandar();
	}
	
	SudokuGUI(int partidaID) throws Exception {
		contructorEstandar();
	}
	
	private void contructorEstandar() throws Exception{
		if (!iniciarBuit)
			controlador.omplirTaulellInicial();
		
		incialitzacio();
		JPanel panel = new JPanel();
		JPanel botonera;
		JButton btnBottom;
		getContentPane().add(taulellGUI);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		taulellGUI.setLayout(new GridLayout(9, 9));
		taulellGUI.setBorder(BorderFactory.createMatteBorder(3, 2, 2, 2, Color.BLACK));
		taulellGUI.setBackground(Color.BLACK);
		botonera = new JPanel();
		getContentPane().add(botonera, BorderLayout.SOUTH);
		botonera.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		btnBottom = new JButton();
		btnBottom.setFont(new Font("Calibri Light", Font.PLAIN, 15));
		btnBottom.setBackground(Color.LIGHT_GRAY);
		btnBottom.setPreferredSize(new Dimension(430, 30));
		btnBottom.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botonera.add(btnBottom);

		if (!iniciarBuit)
			botoEquivalent(btnBottom);
		else
			botoIniciarS(btnBottom);

		generarSudokuGUI();
	}

	private void botoEquivalent(JButton btnBottom) {
		btnBottom.setText("Generar Sudoku Equivalent");
		btnBottom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String theMessage = "Estas segur de voler un Sudoku equivalent? Es perdre l'actual.";
				int result = JOptionPane.showOptionDialog((Component) null, theMessage, "Abans de continuar...",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
						new String[] { "No, continuar amb aquest", "Si, crear Sudoku Equivalent" }, "default");
				if (result == 1)
					try {
						controlador.generarEquivalent();
						taulellGUI.removeAll();
						generarSudokuGUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		});
	}

	private void botoIniciarS(JButton btnBottom) {
		btnBottom.setText("Començar Sudoku");
		btnBottom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					iniciarBuit = false;
					controlador.ferInicials();
					taulellGUI.removeAll();
					btnBottom.removeActionListener(this);
					botoEquivalent(btnBottom);
					generarSudokuGUI();
				} catch (Exception e1) {
					iniciarBuit = true;
					String msg = e1.getMessage();
					String[] msgParts = msg.split(":");
					JOptionPane.showMessageDialog(null, msgParts[1], msgParts[0], JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void generarSudokuGUI() throws Exception {
		int[][] casellaControlador = controlador.entersTaulell();
		for (int fila = 0; fila < MIDA; fila++)
			for (int columna = 0; columna < MIDA; columna++) {
				casella[fila][columna] = null;
				casella[fila][columna] = new CasellaGUI(fila, columna);
				if (controlador.esInicial(fila, columna))
					casella[fila][columna].casellaInicial();

				casella[fila][columna].setText(String.valueOf(casellaControlador[fila][columna]));

				if (!controlador.esInicial(fila, columna)) {
					if (String.valueOf(casellaControlador[fila][columna]).equals("0"))
						esborrarEntrada(fila, columna);
					casella[fila][columna].addKeyListener(new KeyListener() {
						public void keyPressed(KeyEvent e) {
							char caracter = e.getKeyChar();
							String caracterS = Character.toString(caracter);
							int keyCode = e.getKeyCode();
							CasellaGUI casella = (CasellaGUI) e.getSource();
							int f = casella.getFila();
							int c = casella.getColumna();

							try {
								// key Back Space o Delete
								if (keyCode != 8 && keyCode != 127)
									novaEntrada(caracterS, f, c);
								else
									esborrarEntrada(f, c);
							} catch (Exception e1) {
								String msg = e1.getMessage();
								String[] msgParts = msg.split(":");
								JOptionPane.showMessageDialog(null, msgParts[1], msgParts[0],
										JOptionPane.ERROR_MESSAGE);
								try {
									esborrarEntrada(f, c);
								} catch (Exception e2) {
									e1.printStackTrace();
								}
							}
						}

						public void keyReleased(KeyEvent e) {
						}

						public void keyTyped(KeyEvent e) {
						}
					});
				}
				taulellGUI.add(casella[fila][columna]);
			}
		this.setVisible(true);
	}

	private void novaEntrada(String caracterS, int f, int c) throws Exception {
		
		if(iniciarBuit)
			controlador.maximCreacio();

		int[] coordenadaError = null;
		String filaS = String.valueOf(f);
		String columnaS = String.valueOf(c);

		borrarVermell();

		String valorS = casella[f][c].getText();
		valorS = valorS.concat(caracterS);
		coordenadaError = controlador.realitzarJugada(filaS, columnaS, valorS);

		if (coordenadaError != null) {
			controlador.realitzarJugada(filaS, columnaS);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_BACK_SPACE);
			casella[coordenadaError[0]][coordenadaError[1]].setBackground(Color.RED);
		}

		if (controlador.isFinalitzar())
			JOptionPane.showMessageDialog(null, "Felicitats, has emplenat totes les cel.les.", "FI JOC.",
					JOptionPane.INFORMATION_MESSAGE);
	}

	private void esborrarEntrada(int f, int c) throws Exception {
		String filaS = String.valueOf(f);
		String columnaS = String.valueOf(c);
		controlador.realitzarJugada(filaS, columnaS);
		casella[f][c].setText("");
	}

	private void borrarVermell() {
		for (int fila = 0; fila < MIDA; fila++)
			for (int columna = 0; columna < MIDA; columna++)
				casella[fila][columna].borrarVermell();
	}
}