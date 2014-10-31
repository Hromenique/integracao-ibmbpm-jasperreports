package br.com.processmind.datasources;

import teamworks.TWObject;

import teamworks.TWList;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * Implementação de uma fonte de dados (Data Source) que fornece dados contidos em um TWList (tipo list utilizado no IBM BPM)
 * 
 * @author Hromenique Cezniowscki Leite Batista (hromenique@gmail.com)
 *
 */
public class JRTWListDataSource implements JRDataSource{

	private TWList twList;
	private int index = -1;
	
	public JRTWListDataSource(TWList twList) {		
		this.twList = twList;				
	}
	
	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		Object fieldValue = null;
		try {
			TWObject twObject = (TWObject) this.twList.getArrayData(index);
			if (twObject != null) {
				fieldValue = twObject.getPropertyValue(jrField.getName());
			}
		} catch (Exception exception) {
			throw new JRException(exception);
		}
		return fieldValue;
	}

	@Override
	public boolean next() throws JRException {
		try {
			if (this.index >= this.twList.getArraySize() - 1) {
				return false;
			}
		} catch (Exception exception) {
			throw new JRException(exception);
		}

		this.index++;
		return true;
	}
}
