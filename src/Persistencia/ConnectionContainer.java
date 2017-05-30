package Persistencia;

class ConnectionContainer {
	private static ConnectionBBDD connectionBBDD;
	
	public static ConnectionBBDD getConnection() throws Exception{
		if(connectionBBDD == null){
			connectionBBDD = new ConnectionBBDD();
		}
		return connectionBBDD;
	}
	
	static void iniConnection(String usuari, String password) throws Exception {
		if(connectionBBDD == null){
			connectionBBDD = new ConnectionBBDD();
		}
	}

	public static void closeConnection() throws Exception {
		try{
			connectionBBDD.close();
			connectionBBDD = null;
		}
		catch(Exception e){
			throw new Exception("No s'ha tancat la connexi�");
		}
	}
}