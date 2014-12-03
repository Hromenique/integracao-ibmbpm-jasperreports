package br.com.processmind.utils;

import java.io.File;

/**
 * 
 * Classe com métodos utilitários para ser usasdos nas classes de teste
 * 
 * @author Hromenique Cezniowscki Leite Batista
 *
 */
public final class TestUtil {
	private static final String SEPARADOR = File.separator;		
	
	private TestUtil(){
		
	}
	
	public static String obterPathDestinoDoRelatorio() {
		File outputFilePath = new File(SEPARADOR + "relatorios_teste");
		return outputFilePath.getAbsolutePath() + SEPARADOR;
	}

	public static String obterPathDoJRXML() {
		File jasperFilePath = new File("." + SEPARADOR +"src"+ SEPARADOR +"test"+ SEPARADOR + "resources" + SEPARADOR + "relatorio_teste.jrxml");
		return jasperFilePath.getPath();
	}

	public static void criarDiretorioDeRelatorios() {
		File pastaRelatorio = new File(new File(SEPARADOR + "relatorios_teste").getAbsolutePath());		
		if(pastaRelatorio.exists()){
			pastaRelatorio.delete();
		}
		pastaRelatorio.mkdir();
	}
}
