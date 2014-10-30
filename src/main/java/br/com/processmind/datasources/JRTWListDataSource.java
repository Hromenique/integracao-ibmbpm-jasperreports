package br.com.processmind.datasources;

import teamworks.TWList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class JRTWListDataSource implements JRDataSource{

	private TWList twList;
	private int nextPosition;
	private int listSize;
	
	public JRTWListDataSource(TWList twList) {
		//this.listSize = twList.getArraySize();
		this.twList = twList;
		this.nextPosition = 0;		
	}
	
	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean next() throws JRException {
		// TODO Auto-generated method stub
		return false;
	}

}
