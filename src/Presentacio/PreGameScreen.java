package Presentacio;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Aplicacio.Controlador;
import Domini.Jugador;
import Domini.Partida;
import Persistencia.LoginBBDD;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;

public class PreGameScreen extends JFrame {
	
	private Controlador controlador;
	private JPanel contentPane;
	private JLabel lblSenseConnexi;
	private boolean online = false;
	private JTextField textField;
	private Jugador jugador;
	private JList<String> list;
	private List<Object[]> llistatPartides;
	
	String nomJugador = "Anonim";
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PreGameScreen frame = new PreGameScreen();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public PreGameScreen() {
		
		try {
			controlador = new Controlador();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblSenseConnexi = new JLabel("Sense Connexi\u00F3");
		lblSenseConnexi.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSenseConnexi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSenseConnexi.setBounds(10, 10, 414, 14);
		contentPane.add(lblSenseConnexi);
		
		getContentPane().setLayout(null);
		
		
		JLabel lblNomJugador = new JLabel("Nom Jugador: ");
		lblNomJugador.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomJugador.setBounds(25, 36, 92, 14);
		getContentPane().add(lblNomJugador);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setBounds(127, 33, 258, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		

		JButton btnCarregarPartida = new JButton("Carregar Partida");
		btnCarregarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciarSudoku((Integer)llistatPartides.get(list.getSelectedIndex())[0]);
			}
		});
		
		JButton btnIdentificarse = new JButton("Identificar-se");
		btnIdentificarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnIdentificarse.setEnabled(false);
				textField.setEnabled(false);
				list.setEnabled(true);
				btnCarregarPartida.setEnabled(true);
				
				// Load / Create Player
				
				try {
					initJugador();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				//TODO: Display saved games
				
				try {
					checkGameCount();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnIdentificarse.setBounds(260, 74, 125, 23);
		getContentPane().add(btnIdentificarse);
		
		list = new JList<String>();
		list.setEnabled(false);
		list.setBounds(25, 124, 376, 87);
		getContentPane().add(list);
		
		btnCarregarPartida.setEnabled(false);
		btnCarregarPartida.setBounds(212, 222, 173, 23);
		getContentPane().add(btnCarregarPartida);
		
		int result = 1;
		do {
			try {
				result = 1;
				new LoginBBDD();
				online = true;
				lblSenseConnexi.setText("Conectat com: G12GEILAB1");
			} catch (Exception e2) {
				String theMessage = "No s'ha pogut conectar amb la Base de Dades. Vol continuar sense conexió? (No es guardarán les partides jugades)";
				result = JOptionPane.showOptionDialog((Component) null, theMessage, "Error de conexió:",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
						new String[] { "Reintentar", "Continuar sense conexió" }, "default");

			}
		} while (result == 0);

		if (!online)
			iniciarSudoku();
		else {
			
		}
	}
	
	private void iniciarSudoku(){
		try {
			SudokuGUI frame = new SudokuGUI();
			frame.setVisible(true);
			endPreGameScreen();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void iniciarSudoku(int idPartida){
		try {
			SudokuGUI frame = new SudokuGUI(idPartida);
			frame.setVisible(true);
			endPreGameScreen();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void initJugador() throws Exception {
		
		nomJugador = textField.getText();
		controlador.obtenirJugador(nomJugador);
		if (jugador == null)  this.jugador = controlador.crearJugador(nomJugador);
		else this.jugador = controlador.carregarJugador(nomJugador);
	}
	
	public void checkGameCount()throws Exception{
		int games = 0;
		List<Object[]> partides = controlador.obtenirPartides(this.jugador);
		games = partides.size();
		
		if(games == 0) iniciarSudoku();
		else if(games == 1){
			String theMessage = "Vol continuar l'última partida?";
			int result = JOptionPane.showOptionDialog((Component) null, theMessage, "Abans de continuar:",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
					new String[] { "Continuar", "Començar nova" }, "default");
			
			if(result == 1) iniciarSudoku();
			else {
				for(Object[] o : partides){
					 iniciarSudoku((Integer)partides.get(0)[0]);
				}
			}
		}
		else{
			this.llistatPartides = partides;
			for(Object[] o : partides){
				list.add(o[0]+" "+o[1]+" "+o[2],null);
			}
		}
	}
	
	private void endPreGameScreen(){
		this.setVisible(false);
	}
}
