package br.com.processmind.geradordocumento;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * Classe que gera um documento em formato XSL
 * 
 * @author Hromenique Cezniowscki Leite Batista
 *
 */
public class GeradorDocumentoEmXls extends GeradorDocumentoAbstrato {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Exporter getExporter(JasperPrint jasperPrint, String pathDestinoDoDocumento) {
		Exporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pathDestinoDoDocumento));
		return exporter;
	}
}
