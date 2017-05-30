package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Domini.Jugador;

public class JugadorBBDD {
	private ConnectionBBDD connexio;
	
	public JugadorBBDD() throws Exception{
		this.connexio = ConnectionContainer.getConnection();
	}
	
	private boolean existeixJugador(String nom){
		ResultSet result;
		try {
			result = selectJugador(nom);
			return result.next();
		} catch (SQLException e) {
		}
		return false;
	}
	
	public Jugador getJugadorBBDD (String nom) throws SQLException{
		if(!existeixJugador(nom))throw new SQLException();
		ResultSet result=selectJugador(nom);
		result.next();
		int idJugador=result.getInt(1);
		String nomJugador=result.getString(2);
		Jugador j = new Jugador(nomJugador);
		return j;
	}
	
	private ResultSet selectJugador(String nom) throws SQLException{
		String sql="SELECT NOMJUGADOR FROM JUGADOR WHERE UPPER(NOMJUGADOR)=UPPER(?) ";
		PreparedStatement selectStatement=connexio.prepareStatement(sql);
		selectStatement.clearParameters();
		selectStatement.setString(1,nom);
		return selectStatement.executeQuery();
	}
	
	public Jugador trobarJugador(String nom) {
		Jugador jugador = null;
		try {
			jugador = getJugadorBBDD(nom);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return jugador;
	}
	
	public String guardarJugador(Jugador jugador) throws Exception {
		String jugadorNom = "";
		try {
			if(trobarJugador(jugador.getNom()) != null) {
				jugador = getJugadorBBDD(jugador.getNom());
				jugadorNom = jugador.getNom();
			} else {
				jugadorNom = insertarJugador(jugador);
			}
		} catch (SQLException e) {
			throw new Exception("No s'ha guardat el jugador");
		}
		return jugadorNom;
	}
	
	private String insertarJugador(Jugador jugador) throws SQLException, Exception {
		PreparedStatement insertJugador = createInsertQuery(jugador);
		insertJugador.executeUpdate();
		return "1";
	}
	
	private PreparedStatement createInsertQuery(Jugador jugador) throws Exception{
		String sql="INSERT INTO JUGADOR (NOMJUGADOR, ESTAJUGANT) VALUES (?,?)";
		PreparedStatement insertJugadorStatement=connexio.prepareStatement(sql);
		insertJugadorStatement.clearParameters();
		insertJugadorStatement.setString(1, jugador.getNom());
		insertJugadorStatement.setBoolean(2, jugador.getEstaJugant());
		return insertJugadorStatement;
	}
	
	private PreparedStatement createPKValueQuery() throws SQLException{
		String sql ="SELECT S_IDJUGADOR.nextval pkJugador from sys.dual";
		return connexio.prepareStatement(sql);
	}
	
	private int createJugadorId() throws SQLException, Exception{
		ResultSet generatedKey=createPKValueQuery().executeQuery();
		if(generatedKey.next())
		return generatedKey.getInt(1);
		throw new Exception("Invalid PK");
	}
}
