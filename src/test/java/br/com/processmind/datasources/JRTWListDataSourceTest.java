package br.com.processmind.datasources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import net.sf.jasperreports.engine.JRField;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import teamworks.TWList;
import teamworks.TWObject;

/**
 * 
 * @author Hromenique Cezniowscki Leite Batista
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class JRTWListDataSourceTest {
	@Mock
	private JRField jrFieldNome;
	@Mock
	private JRField jrFieldIdade;
	@Mock
	private JRField jrFieldSalario;
	@Mock
	private JRField jrFieldNascimento;	
	@Mock
	private JRField jrFieldInvalido;
	@Mock
	private TWList twList;
	@Mock
	private TWObject twObj1;
	@Mock
	private TWObject twObj2;
	
	private JRTWListDataSource dataSource;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		dataSource = new JRTWListDataSource(twList);
	}	

	@Test
	public void testNext_ComVazio() throws Exception {
		when(twList.getArraySize()).thenReturn(0);		
			
		assertEquals(false, dataSource.next());
		
		verify(twList, Mockito.atLeast(1)).getArraySize();
	}
	
	@Test
	public void testNext_ComUmaPosicao() throws Exception {
		when(twList.getArraySize()).thenReturn(1);			
		
		assertEquals(false, dataSource.next());
		
		verify(twList, Mockito.atLeast(1)).getArraySize();
	}
	
	@Test
	public void testNext_Com2Posicoes() throws Exception {
		when(twList.getArraySize()).thenReturn(2);	
		
		assertEquals(true, dataSource.next());		
		assertEquals(false, dataSource.next());
		
		verify(twList, Mockito.atLeast(1)).getArraySize();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetFieldValue_Existente() throws Exception {
		when(twList.getArrayData(0)).thenReturn(twObj1);
		
		when(twObj1.getPropertyValue("nome")).thenReturn("Fulano");
		when(twObj1.getPropertyValue("idade")).thenReturn(24);
		when(twObj1.getPropertyValue("salario")).thenReturn(1200.0);
		when(twObj1.getPropertyValue("nascimento")).thenReturn(new Date(2000, 10, 10));
		
		when(jrFieldNome.getName()).thenReturn("nome");
		when(jrFieldIdade.getName()).thenReturn("idade");
		when(jrFieldSalario.getName()).thenReturn("salario");
		when(jrFieldNascimento.getName()).thenReturn("nascimento");
		
		Object fieldValueNome = dataSource.getFieldValue(jrFieldNome);
		Object fieldValueIdade = dataSource.getFieldValue(jrFieldIdade);
		Object fieldValueSalario = dataSource.getFieldValue(jrFieldSalario);
		Object fieldValueNascimento = dataSource.getFieldValue(jrFieldNascimento);
		
		assertThat(fieldValueNome, Matchers.instanceOf(String.class));
		assertThat(fieldValueIdade, Matchers.instanceOf(Integer.class));
		assertThat(fieldValueSalario, Matchers.instanceOf(Double.class));
		assertThat(fieldValueNascimento, Matchers.instanceOf(Date.class));
		
		assertEquals(fieldValueNome, "Fulano");
		assertEquals(fieldValueNome, 24);
		assertEquals(fieldValueNome, 1200.0);
		assertEquals(fieldValueNome, new Date(2000, 10, 10));
		
		verify(twList, Mockito.times(1)).getArrayData(0);
		
		verify(jrFieldNome, Mockito.times(1)).getName();
		verify(jrFieldIdade, Mockito.times(1)).getName();
		verify(jrFieldSalario, Mockito.times(1)).getName();
		verify(jrFieldNascimento, Mockito.times(1)).getName();	
		
		verify(twObj1, Mockito.times(1)).getPropertyValue("nome");
		verify(twObj1, Mockito.times(1)).getPropertyValue("idade");
		verify(twObj1, Mockito.times(1)).getPropertyValue("salario");
		verify(twObj1, Mockito.times(1)).getPropertyValue("nascimento");		
	}
	
	@Test
	public void testGetFieldValue_NaoExistente() throws Exception{		
		when(jrFieldInvalido.getName()).thenReturn("campoDeNomeInvalido");		
		
		assertNull(dataSource.getFieldValue(jrFieldInvalido));
		
		verify(jrFieldInvalido, Mockito.times(1)).getName();
	}

}
