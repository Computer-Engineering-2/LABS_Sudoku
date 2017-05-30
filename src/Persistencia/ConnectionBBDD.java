package Persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionBBDD extends AbsConnectionBBDD {
	private final String urlConnection = "jdbc:oracle:thin:@kali.eupmt.tecnocampus.cat:1521:sapiens";
	private Connection connexio;
	
	ConnectionBBDD() throws Exception{
		try{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connexio = DriverManager.getConnection(urlConnection, "G12GEILAB1", "G8GEILAB1144");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("Error en la connexi� Oracle. " + e.getMessage());
		}
	}
	
	@Override
	public Statement createStatement() throws SQLException {
		return connexio.createStatement();
	}
	
	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return connexio.prepareStatement(sql);
	}
	
	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return connexio.prepareCall(sql);
	}
	
	@Override
	public void close() throws SQLException {
		connexio.close();
	}
	
	@Override
	public boolean isClosed() throws SQLException {
		return connexio.isClosed();
	}
}
