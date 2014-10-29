package br.com.processmind.datasources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Date;
import net.sf.jasperreports.engine.JRField;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import teamworks.TWList;
import teamworks.TWObject;
import teamworks.TWObjectFactory;

public class JRTWListDataSourceTest {
	@Mock
	JRField jrFieldNome;
	@Mock
	JRField jrFieldIdade;
	@Mock
	JRField jrFieldSalario;
	@Mock
	JRField jrFieldNascimento;	
	@Mock
	JRField jrFieldInvalido;

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testNext_ComVazio() throws Exception {
		TWList twList = criarTWListVazio();
		JRTWListDataSource dataSource = criarDataSource(twList);		
		
		assertEquals(false, dataSource.next());
	}
	
	@Test
	public void testNext_ComUmaPosicao() throws Exception {
		TWList twList = criarTWListComUmaPosicao();
		JRTWListDataSource dataSource = criarDataSource(twList);		
		
		assertEquals(false, dataSource.next());
	}
	
	@Test
	public void testNext_Com3Posicoes() throws Exception {
		TWList twList = criarTWListCom3Posicoes();
		JRTWListDataSource dataSource = criarDataSource(twList);		
		
		assertEquals(true, dataSource.next());
		assertEquals(true, dataSource.next());
		assertEquals(false, dataSource.next());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetFieldValue_Valido() throws Exception {
		TWList twList = criarTWListComUmaPosicao();
		JRTWListDataSource dataSource = criarDataSource(twList);		
		
		when(jrFieldNome.getName()).thenReturn("nome");
		when(jrFieldIdade.getName()).thenReturn("idade");
		when(jrFieldSalario.getName()).thenReturn("salario");
		when(jrFieldNascimento.getName()).thenReturn("nascimento");
		
		Object fieldValueNome = dataSource.getFieldValue(jrFieldNome);
		Object fieldValueIdade = dataSource.getFieldValue(jrFieldIdade);
		Object fieldValueSalario = dataSource.getFieldValue(jrFieldSalario);
		Object fieldValueNascimento = dataSource.getFieldValue(jrFieldNascimento);
		
		verify(jrFieldNome, Mockito.times(1)).getName();
		verify(jrFieldIdade, Mockito.times(1)).getName();
		verify(jrFieldSalario, Mockito.times(1)).getName();
		verify(jrFieldNascimento, Mockito.times(1)).getName();		
		
		assertThat(fieldValueNome, Matchers.instanceOf(String.class));
		assertThat(fieldValueIdade, Matchers.instanceOf(Integer.class));
		assertThat(fieldValueSalario, Matchers.instanceOf(Double.class));
		assertThat(fieldValueNascimento, Matchers.instanceOf(Date.class));
		
		assertEquals(fieldValueNome, "Fulano");
		assertEquals(fieldValueNome, 24);
		assertEquals(fieldValueNome, 1200.0);
		assertEquals(fieldValueNome, new Date(2000, 10, 10));
	}
	
	@Test
	public void testGetFieldValue_Invalido() throws Exception{
		TWList twList = criarTWListComUmaPosicao();
		JRTWListDataSource dataSource = criarDataSource(twList);
		
		when(jrFieldInvalido.getName()).thenReturn("campoDeNomeInvalido");
		
		Object object = dataSource.getFieldValue(jrFieldInvalido);
		
		verify(jrFieldInvalido, Mockito.times(1)).getName();
		
		assertNull(object);
	}
	

	@SuppressWarnings("deprecation")
	private TWList criarTWListCom3Posicoes() throws Exception{
		TWList twList = TWObjectFactory.createList();	
		
		TWObject object1 = TWObjectFactory.createObject();
		TWObject object2 = TWObjectFactory.createObject();
		TWObject object3 = TWObjectFactory.createObject();
		
		object1.setPropertyValue("nome", "Fulano");
		object1.setPropertyValue("idade", 24);
		object1.setPropertyValue("salario", 1200.0);
		object1.setPropertyValue("nascimento", new Date(2000, 10, 10));
		
		object2.setPropertyValue("nome", "Deltrano");
		object2.setPropertyValue("idade", 30);
		object2.setPropertyValue("salario", 500.0);
		object2.setPropertyValue("nascimento", new Date(1994, 10, 7));
		
		object3.setPropertyValue("nome", "Siclano");
		object3.setPropertyValue("idade", 31);
		object3.setPropertyValue("salario", 5100.0);
		object3.setPropertyValue("nascimento", new Date(1983, 7, 10));
		
		twList.addArrayData(object1);
		twList.addArrayData(object2);
		twList.addArrayData(object3);	
		
		return twList;
	}
	
	@SuppressWarnings("deprecation")
	private TWList criarTWListComUmaPosicao() throws Exception{
		TWList twList = TWObjectFactory.createList();		
		TWObject object = TWObjectFactory.createObject();		
		
		object.setPropertyValue("nome", "Fulano");
		object.setPropertyValue("idade", 24);
		object.setPropertyValue("salario", 1200.0);
		object.setPropertyValue("nascimento", new Date(2000, 10, 10));
		
		return twList;
	}
	
	private TWList criarTWListVazio() throws Exception{
		TWList twList = TWObjectFactory.createList();	
		return twList;
	}
	
	private JRTWListDataSource criarDataSource(TWList twList) {
		JRTWListDataSource dataSource = new JRTWListDataSource(twList);
		return dataSource;
	}
}
