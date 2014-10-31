package br.com.processmind.datasources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import net.sf.jasperreports.engine.JRException;
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
 * @author Hromenique Cezniowscki Leite Batista (hromenique@gmail.com)
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
	}
	
	@Test(expected=JRException.class)
	public void testNext_ComTWListIgualANull() throws JRException{
		
		dataSource = new JRTWListDataSource(null);
		dataSource.next();
	}

	@Test
	public void testNext_ZeroPosicoes() throws Exception {
		
		when(twList.getArraySize()).thenReturn(0);		
			
		dataSource = new JRTWListDataSource(twList);
		//mover para a primeira posição
		assertEquals(false, dataSource.next()); 
		//força o movimento além da primeira posição
		assertEquals(false, dataSource.next()); 
		
		verify(twList, Mockito.atLeast(1)).getArraySize();
	}
	
	@Test
	public void testNext_ComUmaPosicao() throws Exception {
		when(twList.getArraySize()).thenReturn(1);			
		
		dataSource = new JRTWListDataSource(twList);
		//mover para a primeira posição
		assertEquals(true, dataSource.next());
		//força o movimento além da primeira posição
		assertEquals(false, dataSource.next());
		
		verify(twList, Mockito.atLeast(1)).getArraySize();
	}
	
	@Test
	public void testNext_Com2Posicoes() throws Exception {
		when(twList.getArraySize()).thenReturn(2);	
		
		dataSource = new JRTWListDataSource(twList);
		//mover para a primeira posição
		assertEquals(true, dataSource.next());
		assertEquals(true, dataSource.next());		
		assertEquals(false, dataSource.next());
		//força o movimento além da última posição
		assertEquals(false, dataSource.next());
		
		verify(twList, Mockito.atLeast(1)).getArraySize();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetFieldValue_ComUmaPosicao() throws Exception {
		when(twList.getArraySize()).thenReturn(1);	
		when(twList.getArrayData(0)).thenReturn(twObj1);
		
		prepararMockTWObject(twObj1, "Fulano", 24, 1200.0, new Date(2000, 10, 10));		
		prepararMockJRField();
		
		dataSource = new JRTWListDataSource(twList);
		//mover para a primeira posição
		dataSource.next();
		Object fieldValueNome = dataSource.getFieldValue(jrFieldNome);
		Object fieldValueIdade = dataSource.getFieldValue(jrFieldIdade);
		Object fieldValueSalario = dataSource.getFieldValue(jrFieldSalario);
		Object fieldValueNascimento = dataSource.getFieldValue(jrFieldNascimento);
		
		assertThat(fieldValueNome, Matchers.instanceOf(String.class));
		assertThat(fieldValueIdade, Matchers.instanceOf(Integer.class));
		assertThat(fieldValueSalario, Matchers.instanceOf(Double.class));
		assertThat(fieldValueNascimento, Matchers.instanceOf(Date.class));
		
		assertEquals(fieldValueNome, "Fulano");
		assertEquals(fieldValueIdade, 24);
		assertEquals(fieldValueSalario, 1200.0);
		assertEquals(fieldValueNascimento, new Date(2000, 10, 10));		
		
		verify(twList, Mockito.atLeast(1)).getArrayData(0);
		
		verifyMockJRField();		
		verifyMockTWObject(twObj1);				
	}

	@Test	
	@SuppressWarnings("deprecation")
	public void testGetFieldValue_Com2Posicoes() throws JRException{
		when(twList.getArraySize()).thenReturn(2);	
		when(twList.getArrayData(0)).thenReturn(twObj1);
		when(twList.getArrayData(1)).thenReturn(twObj2);
		
		prepararMockTWObject(twObj1, "Fulano", 24, 1200.0, new Date(2000, 10, 10));	
		prepararMockTWObject(twObj2, "Siclano", 30, 5000.0, new Date(1983, 7, 10));	
		prepararMockJRField();
		
		dataSource = new JRTWListDataSource(twList);
		//mover para a primeira posição
		dataSource.next();
		//mover para a segunda posição
		dataSource.next();
		
		Object fieldValueNome = dataSource.getFieldValue(jrFieldNome);
		Object fieldValueIdade = dataSource.getFieldValue(jrFieldIdade);
		Object fieldValueSalario = dataSource.getFieldValue(jrFieldSalario);
		Object fieldValueNascimento = dataSource.getFieldValue(jrFieldNascimento);
		
		assertThat(fieldValueNome, Matchers.instanceOf(String.class));
		assertThat(fieldValueIdade, Matchers.instanceOf(Integer.class));
		assertThat(fieldValueSalario, Matchers.instanceOf(Double.class));
		assertThat(fieldValueNascimento, Matchers.instanceOf(Date.class));
		
		assertEquals(fieldValueNome, "Siclano");
		assertEquals(fieldValueIdade, 30);
		assertEquals(fieldValueSalario, 5000.0);
		assertEquals(fieldValueNascimento, new Date(1983, 7, 10));
		assertEquals("Teste de movimento para posição inválida",false, dataSource.next());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetFieldValue_ComJRFieldInvalido() throws Exception{		
		when(twList.getArraySize()).thenReturn(1);	
		when(twList.getArrayData(0)).thenReturn(twObj1);
		when(jrFieldInvalido.getName()).thenReturn("campoDeNomeInvalido");	
		prepararMockTWObject(twObj1, "Fulano", 24, 1200.0, new Date(2000, 10, 10));	
		
		dataSource = new JRTWListDataSource(twList);
		dataSource.next();
		assertNull(dataSource.getFieldValue(jrFieldInvalido));
		
		verify(jrFieldInvalido, Mockito.times(1)).getName();
	}

	private void prepararMockTWObject(TWObject twObj, String nome, int idade, double salario, Date nascimento) {
		when(twObj.getPropertyValue("nome")).thenReturn(nome);
		when(twObj.getPropertyValue("idade")).thenReturn(idade);
		when(twObj.getPropertyValue("salario")).thenReturn(salario);
		when(twObj.getPropertyValue("nascimento")).thenReturn(nascimento);
	}
	
	private void verifyMockTWObject(TWObject twObj) {
		verify(twObj, Mockito.times(1)).getPropertyValue("nome");
		verify(twObj, Mockito.times(1)).getPropertyValue("idade");
		verify(twObj, Mockito.times(1)).getPropertyValue("salario");
		verify(twObj, Mockito.times(1)).getPropertyValue("nascimento");
	}

	private void prepararMockJRField() {
		when(jrFieldNome.getName()).thenReturn("nome");
		when(jrFieldIdade.getName()).thenReturn("idade");
		when(jrFieldSalario.getName()).thenReturn("salario");
		when(jrFieldNascimento.getName()).thenReturn("nascimento");
	}

	private void verifyMockJRField() {
		verify(jrFieldNome, Mockito.times(1)).getName();
		verify(jrFieldIdade, Mockito.times(1)).getName();
		verify(jrFieldSalario, Mockito.times(1)).getName();
		verify(jrFieldNascimento, Mockito.times(1)).getName();
	}

}
