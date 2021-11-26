package com.hepta.funcionarios.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.hepta.funcionarios.entity.Funcionario;
import com.hepta.funcionarios.entity.Setor;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FuncionarioServiceTest {
	
	private static Setor setor;
	private static Funcionario funcionario;
	
	/**
	 * Defini��o da classe a ser testada e do funcion�rio que ser� utilizado como teste.
	 * @throws Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		setor = new Setor();
		setor.setId(1);
		setor.setNome("Desenvolvimento");
		
		funcionario = new Funcionario();
		funcionario.setNome("Hepta");
		funcionario.setEmail("hepta@teste.com");
		funcionario.setIdade(24);
		funcionario.setSalario(5000.00);
		funcionario.setSetor(setor);
		
		
	}

	/**
	 * Inicialmente se testa se o funcionario teste ser� criado com sucesso no banco de dados.
	 */
	@Test
	@Order(1)
	void testFuncionarioCreate() {
		FuncionarioService service = new FuncionarioService();
		Response response = null;
		try {		
			response = service.FuncionarioCreate(funcionario);				
		} catch (Exception e) {
			e.printStackTrace();
			fail (e.toString());
		}
		
		assertEquals(200, response.getStatus(),"Erro interno na criacao do Funcionario");	
	}

	/**
	 * Depois, � testada a atualiza��o do funcionario. S�o dois testes, um confere se a opera��o no BD foi conclu�da, 
	 * e o outro confere se o Funcion�rio foi realmente atualizado.
	 * O aviso "unchecked" foi suprimido pois se o tipo estiver incorreto o teste falhar�
	 */
	@Test
	@Order(2)
	@SuppressWarnings("unchecked")
	void testFuncionarioUpdate() {
		FuncionarioService service = new FuncionarioService();
		List<Funcionario> lista = null;
		
		try {
		lista = (List<Funcionario>) service.FuncionarioRead().getEntity();
		}catch(Exception e) {
			e.printStackTrace();
			fail("Erro interno ao resgatar a lista de Funcion�rios");
		}
		
		Funcionario aux = lista.get(lista.size()-1);		
		Response response = null;
		
		aux.setNome("Nathan Vinhadelli");
		try {
			response = service.FuncionarioUpdate(aux.getId(), aux);
		}catch (Exception e)
		{
			e.printStackTrace();
			fail("Erro interno na atualiza��o do Funcion�rio");
		}
			
		funcionario = (Funcionario) response.getEntity();
		
		assertEquals("Nathan Vinhadelli", funcionario.getNome(),"Falha na atualiza��o do Funcion�rio");		
	}
	
	/**
	 * O terceiro teste confere se o nosso funcion�rio atualizado se encontra na base de dados, 
	 * testando ao mesmo tempo a atualiza��o e a leitura.
	 * O aviso "unchecked" foi suprimido pois se o tipo estiver incorreto o teste falhar�
	 */
	@Test
	@Order(3)
	@SuppressWarnings("unchecked")
	void testFuncionarioRead() {
		FuncionarioService service = new FuncionarioService();
		Response response = null;
		List<Funcionario> lista = null;
		
		try {
			response = service.FuncionarioRead();			 			
		} catch (Exception e) {
			e.printStackTrace();
			fail (e.toString());
		}
		lista = (List<Funcionario>) response.getEntity();
		
		assertEquals(funcionario.getNome(), lista.get(lista.size()-1).getNome(), "Lista retornada n�o contem o funcion�rio de teste");
	}

	/**
	 * No teste final, deletamos o usu�rio de teste, para n�o poluir o banco com informa��es imprecisas.
	 */
	@Test
	@Order(4)
	void testFuncionarioDelete() {
		FuncionarioService service = new FuncionarioService();
		Response response = null;
		try {
			response = service.FuncionarioDelete(funcionario.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail (e.toString());
		}
		
		assertEquals(200, response.getStatus(),"Erro interno na exclus�o do Funcion�rio");
	}

}
