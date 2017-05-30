package Persistencia;

public class LoginBBDD {
	public LoginBBDD(){
		
	}
	
	public void logIn(String usuari, String password) throws Exception{
		ConnectionContainer.iniConnection(usuari, password);
	}

	public void closeConnection() throws Exception {
		ConnectionContainer.closeConnection();
	}
}