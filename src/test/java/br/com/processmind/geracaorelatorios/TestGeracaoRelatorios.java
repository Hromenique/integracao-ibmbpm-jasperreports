package br.com.processmind.geracaorelatorios;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.sql.Timestamp;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.processmind.datasources.JRTWListDataSource;
import teamworks.TWList;
import teamworks.TWObject;

@RunWith(MockitoJUnitRunner.class)
public class TestGeracaoRelatorios {

	final String SEPARADOR = File.separator;
	
	@Mock
	private TWList twList;
	@Mock
	private TWObject twObj1;
	@Mock
	private TWObject twObj2;
	
	@Before
	public void init(){
		criarDiretorioDeRelatorios();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGerarRelatoriosComJRTWListDataSource() throws JRException{
		
		preprarMockTWObject(twObj1, 1, 2, "Fulano", "Siclano", new Timestamp(1994, 2, 10, 0, 0, 0, 0), new Timestamp(1994, 2, 10, 0, 0, 0, 0), 300.0);
		preprarMockTWObject(twObj2, 4, 45, "Goku", "Vegeta", new Timestamp(1994, 2, 10, 0, 0, 0, 0), new Timestamp(1994, 2, 10, 0, 0, 0, 0), 8000.0);
		when(twList.getArraySize()).thenReturn(2);
		when(twList.getArrayData(0)).thenReturn(twObj1);
		when(twList.getArrayData(1)).thenReturn(twObj2);
		when(twList.getArrayData(2)).thenReturn(null);			
		
		JRTWListDataSource dataSource = new JRTWListDataSource(twList);
		
		JasperReport jasperReport = JasperCompileManager.compileReport(obterPathDoJRXML());			
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
		JasperExportManager.exportReportToPdfFile(jasperPrint, obterPathDestinoDoRelatorio());	
		
		assertTrue("Teste Relatório gerado",new File(obterPathDestinoDoRelatorio()).exists());		
	}

	private String obterPathDestinoDoRelatorio() {
		File outputFilePath = new File(SEPARADOR+"relatorios_teste"+SEPARADOR+"relatorio_teste.pdf");
		return outputFilePath.getAbsolutePath();
	}

	private String obterPathDoJRXML() {
		File jasperFilePath = new File("."+SEPARADOR+"src"+SEPARADOR+"test"+SEPARADOR+"resources"+SEPARADOR+"relatorio_teste.jrxml");
		return jasperFilePath.getPath();
	}

	private void criarDiretorioDeRelatorios() {
		File pastaRelatorio = new File(new File(SEPARADOR+"relatorios_teste").getAbsolutePath());		
		if(pastaRelatorio.exists()){
			pastaRelatorio.delete();
		}
		pastaRelatorio.mkdir();
	}

	private void preprarMockTWObject(TWObject twObj, int protocolo, int sequencia, String segurado, String reclamante, Timestamp dataAviso, Timestamp dataOcorrencia, double valor) {
		when(twObj.getPropertyValue("num_protocolo")).thenReturn(protocolo);
		when(twObj.getPropertyValue("num_seq")).thenReturn(sequencia);
		when(twObj.getPropertyValue("nom_segurado")).thenReturn(segurado);
		when(twObj.getPropertyValue("nom_reclama")).thenReturn(reclamante);
		when(twObj.getPropertyValue("dat_avis_sin")).thenReturn(dataAviso);
		when(twObj.getPropertyValue("dat_ocor_sin")).thenReturn(dataOcorrencia);
		when(twObj.getPropertyValue("val_estim_sin")).thenReturn(valor);
	}

}
