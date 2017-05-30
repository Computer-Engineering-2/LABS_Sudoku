package Aplicacio;

import Domini.Taulell;
import Prova.GraellaInicial;

public abstract class AbsGraella {
	
	public static void graella (Taulell taulell) throws Exception {
		
		GraellaInicial inicial = new GraellaInicial(taulell);
		
		inicial.nInicial(taulell);
		
	}
	
	public abstract void nInicial (Taulell taulell) throws Exception;

}