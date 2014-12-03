package br.com.processmind.geradordocumento;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;

/**
 * Classe que gera um documento em formato HTML
 * 
 * @author Hromenique Cezniowscki Leite Batista
 *
 */
public class GeradorDocumentoEmHtml extends GeradorDocumentoAbstrato {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Exporter getExporter(JasperPrint jasperPrint, String pathDestinoDoDocumento) {
		Exporter exporter = new HtmlExporter();
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));		
		exporter.setExporterOutput(new SimpleHtmlExporterOutput(pathDestinoDoDocumento));
		return exporter;
	}
}
