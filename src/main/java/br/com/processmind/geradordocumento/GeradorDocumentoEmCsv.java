package br.com.processmind.geradordocumento;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

/**
 * Classe que gera um documento em formato CSV
 * 
 * @author Hromenique Cezniowscki Leite Batista
 *
 */
public class GeradorDocumentoEmCsv extends GeradorDocumentoAbstrato {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Exporter getExporter(JasperPrint jasperPrint, String pathDestinoDoDocumento) {
		Exporter exporter = new JRCsvExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleWriterExporterOutput(pathDestinoDoDocumento));
		return exporter;
	}
}
