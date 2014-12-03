package br.com.processmind.geradordocumento;

import br.com.processmind.datasources.JRTWListDataSource;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.export.Exporter;
import teamworks.TWList;

/**
 * 
 * Classe que abstrai a lógica de geração de um documento
 * 
 * @author Hromenique Cezniowscki Leite Batista
 *
 */
public abstract class GeradorDocumentoAbstrato{
		
	@SuppressWarnings("rawtypes")
	abstract protected Exporter getExporter(JasperPrint jasperPrint, String pathDestinoDoDocumento);
	
	@SuppressWarnings("rawtypes")
	public void geraDocumento(String pathTemplateJRXML, String pathDestinoDoDocumento, TWList dados) throws JRException {			
		JRDataSource datasource = new JRTWListDataSource(dados);		
		JasperReport jasperReport = JasperCompileManager.compileReport(pathTemplateJRXML);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, datasource);
		
		Exporter exporter = getExporter(jasperPrint, pathDestinoDoDocumento);		

		exporter.exportReport();
	}
}
