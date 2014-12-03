package br.com.processmind.geradordocumento;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;

import net.sf.jasperreports.engine.JRException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import teamworks.TWList;
import teamworks.TWObject;
import br.com.processmind.utils.TestUtil;

@RunWith(Parameterized.class)
public class GeradorDocumentoTest {
	
	private GeradorDocumentoAbstrato geradorDocumento;
	private String pathTemplateJRXML;
	private String pathDestinoDoDocumento;
	
	@Mock
	private TWList twList;
	@Mock
	private TWObject twObj1;
	@Mock
	private TWObject twObj2;
	@Mock
	private TWObject twObj3;	
	
	public GeradorDocumentoTest(GeradorDocumentoAbstrato geradorDocumento, String pathTemplateJRXML, String pathDestinoDoDocumento) {
		this.geradorDocumento = geradorDocumento;	
		this.pathTemplateJRXML = pathTemplateJRXML;
		this.pathDestinoDoDocumento = pathDestinoDoDocumento;
	}
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);			
	}
	
	@BeforeClass
	public static void initClass(){
		TestUtil.criarDiretorioDeRelatorios();	
	}
	
	@Parameters
	public static Collection<Object[]> getParametrosDeTeste(){
		Object pathTemplate = TestUtil.obterPathDoJRXML();
		String pathDestino = TestUtil.obterPathDestinoDoRelatorio();
		Object[][] params = new Object[][]{
				{new GeradorDocumentoEmCsv(), pathTemplate, pathDestino + "teste.csv"},
				{new GeradorDocumentoEmDocx(), pathTemplate,pathDestino + "teste.docx"},
				{new GeradorDocumentoEmHtml(), pathTemplate,pathDestino + "teste.html"},
				{new GeradorDocumentoEmPdf(), pathTemplate, pathDestino + "teste.pdf"},
				{new GeradorDocumentoEmXls(), pathTemplate, pathDestino + "teste.xls"}
		};
		
		return Arrays.asList(params);
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void testGeraDocumento() throws FileNotFoundException, IllegalArgumentException, JRException {	
		preprarMockTWObject(twObj1, 1, 2, "Fulano", "Siclano", new Timestamp(1994, 2, 10, 0, 0, 0, 0), new Timestamp(1994, 2, 10, 0, 0, 0, 0), 300.0);
		preprarMockTWObject(twObj2, 4, 45, "Goku", "Vegeta", new Timestamp(1994, 2, 10, 0, 0, 0, 0), new Timestamp(1994, 2, 10, 0, 0, 0, 0), 8000.0);
		preprarMockTWObject(twObj3, 100, 405, "Vaca", "Frango", new Timestamp(1994, 2, 10, 0, 0, 0, 0), new Timestamp(1994, 2, 10, 0, 0, 0, 0), 2400.0);
		when(twList.getArraySize()).thenReturn(3);
		when(twList.getArrayData(0)).thenReturn(twObj1);
		when(twList.getArrayData(1)).thenReturn(twObj2);
		when(twList.getArrayData(2)).thenReturn(twObj3);
		when(twList.getArrayData(3)).thenReturn(null);		
		
		this.geradorDocumento.geraDocumento(pathTemplateJRXML, pathDestinoDoDocumento, twList);
		
		assertTrue(new File(this.pathDestinoDoDocumento).exists());		
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
