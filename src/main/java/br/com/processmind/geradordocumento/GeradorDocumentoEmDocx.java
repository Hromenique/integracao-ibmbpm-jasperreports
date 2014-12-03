package br.com.processmind.geradordocumento;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * Classe que gera um documento em formato DOCX
 * 
 * @author Hromenique Cezniowscki Leite Batista
 *
 */
public class GeradorDocumentoEmDocx extends GeradorDocumentoAbstrato {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Exporter getExporter(JasperPrint jasperPrint, String pathDestinoDoDocumento) {
		Exporter exporter = new JRDocxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pathDestinoDoDocumento));
		return exporter;
	}
}
