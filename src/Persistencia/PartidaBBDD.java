package Persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Domini.Jugador;
import Domini.Partida;


public class PartidaBBDD {
	
	private ConnectionBBDD connexio;

	public final static String SQL_SELECT = "";
	
	
	/***
	 * Constructor que connecta
	 * @throws Exception
	 */
	public PartidaBBDD() throws Exception{
		connexio=ConnectionContainer.getConnection();
	}
	
	/***
	 * Guarda la partida del jugador i el seu estat actual
	 * @param jugadorNom Nom del jugador
	 * @param estat actual del joc, mida, moviments i data de guardat
	 * @return retorna l'identificador de partida guardada
	 * @throws Exception Si no ha pugut guardar la partida
	 */
	public int guardarPartida(String jugadorNom, Partida joc) throws Exception {

		int partidaId = joc.getId();
		try {
			JugadorBBDD jBD = new JugadorBBDD();
			long jugadorId = jBD.guardarJugador(new Jugador(jugadorNom));

			if(partidaId==0) {
				partidaId = this.createPartidaId();

				String sql="INSERT INTO SUDOKU(NOMJUGADOR, IDSUDOKU, DATACREACIO) VALUES (?,?,?)";
				PreparedStatement insertQuery=connexio.prepareStatement(sql);

				insertQuery.clearParameters();
				insertQuery.setLong(1, jugadorId);
				insertQuery.setInt(2, partidaId);
				insertQuery.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
				
				
				insertQuery.executeQuery();
				
				joc.setId(partidaId);
				
			} else {
				partidaId = joc.getId();

				String sql="UPDATE SUDOKU SET DATACREACIO = ? WHERE IDSUDOKU = ?";
				PreparedStatement updateQuery=connexio.prepareStatement(sql);

				updateQuery.clearParameters();
				updateQuery.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
				updateQuery.setInt(2, partidaId);
				
				updateQuery.executeQuery();
			
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al guardar partida");
		}
		return partidaId;
	}


	
	/***
	 * Obtenir partida a partir de l'identificador
	 * @param partidaId
	 * @return Partida
	 * @throws Exception
	 */
	public Partida obtenirPartida (int partidaId) throws Exception{
		
		if(!existeixPartida(partidaId))throw new Exception("No hi ha partida");

		ResultSet result=executeSelectPartida(partidaId);
		result.next();

		Timestamp data = result.getTimestamp(2);
		
		Partida partida= new Partida(partidaId);
		partida.setData(data);
		
		return partida;
	}

	/***
	 * Comprova que existeix la partida a partir de l'identificador
	 * @param partidaId
	 * @return Cert o Fals
	 */
	public boolean existeixPartida(int partidaId){
		ResultSet result;
		try {
			result = executeSelectPartida(partidaId);
			return result.next();
		} catch (SQLException e) {
		}
		return false;
	}	

	/***
	 * Elimina la partida i els seus moviments a partir de seu identificador
	 * @param partidaId
	 * @throws Exception
	 */
	public boolean eliminarPartida(int partidaId) throws Exception {
		try {
			return (executeDeletePartida(partidaId)>0);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error al eliminar partida");
		}
	}
	

	/***
	 * Obtenir informació de les partides guardades d'un jugador
	 * @param jugador
	 * @return Llista d'objectes(partidaId, saveDate, mida, moviments)
	 * @throws SQLException
	 */
	public List<Object[]> descripcioPartides(Jugador jugador) throws SQLException{
		List<Object[]> list = new ArrayList<Object[]>();

		if(jugador!= null && jugador.getNom() != "") {
			ResultSet result = executeConsultaPartides(jugador.getNom());
			while(result.next()){
				Integer partidaId = result.getInt(1);
				String nomJugador = result.getString(2);
				Timestamp saveDate = Timestamp.valueOf(result.getString(3));
				
				Object[] s = {partidaId, nomJugador, saveDate};
				list.add(s);
			}
		}
		
		return list;
	}
	
	
	//////////////////////////////////////
	///// EXECUCIONS A BASE DE DADES /////
	
	/***
	 * Consulta per obtenir informació de les partides d'un jugador
	 * @param jugadorId
	 * @return
	 * @throws SQLException
	 */
	private ResultSet executeConsultaPartides(String nomJugador) throws SQLException{

		String sql = "SELECT SUDOKU.IDSUDOKU, JUGADOR.NOMJUGADOR, DATACREACIO";
		sql +=" FROM SUDOKU";
		sql +=" JOIN JUGADOR ON SUDOKU.NOMJUGADOR = JUGADOR.NOMJUGADOR";
		sql +=" WHERE SUDOKU.NOMJUGADOR=?";
		sql +=" ORDER BY data DESC";
		
		PreparedStatement statement=connexio.prepareStatement(sql);
		statement.clearParameters();
		statement.setString(1, nomJugador);
		return statement.executeQuery();
	}

	/***
	 * Consulta per obtenir la partida a partir del seu identificador
	 * @param partidaId
	 * @return
	 * @throws SQLException
	 */
	private ResultSet executeSelectPartida(int partidaId) throws SQLException{
		String sql="SELECT IDSUDOKU, DATACREACIO FROM SUDOKU WHERE IDSUDOKU=?";
		PreparedStatement statement=connexio.prepareStatement(sql);
		statement.clearParameters();
		statement.setInt(1, partidaId);
		return statement.executeQuery();
	}

	private int executeDeletePartida(int partidaId) throws SQLException{
		String sql="DELETE SUDOKU WHERE IDSUDOKU = ?";
		PreparedStatement statement=connexio.prepareStatement(sql);
		statement.clearParameters();
		statement.setInt(1, partidaId);
		return statement.executeUpdate();
	}
	
	/***
	 * Crea nou identificador de partida
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	private int createPartidaId() throws SQLException, Exception{
		
		try {
			ResultSet generatedKey=createPKValueQuery().executeQuery();
			if(generatedKey.next())
			return generatedKey.getInt(1);
			
		} catch(Exception e) {
			e.printStackTrace();			
			throw new Exception("Invalid PK");
		}
		return 0;
	}
	
	/***
	 * Prepara la sentencia per crear el nou identificador
	 * @return PreparedStatement 
	 * @throws SQLException
	 */
	private PreparedStatement createPKValueQuery() throws SQLException{
		String sql ="SELECT IDSUDOKU.nextval PKSUDOKU from sys.dual";
		return connexio.prepareStatement(sql);
	}		
}
