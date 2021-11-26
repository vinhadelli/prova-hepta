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

import com.hepta.funcionarios.entity.Setor;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SetorServiceTest {
	private static Setor setor;
	
	/**
	 * Defini��o da classe a ser testada e do funcion�rio que ser� utilizado como teste.
	 * @throws Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		setor = new Setor();
		setor.setNome("Setor de Teste");
	}
	
	/**
	 * Essa fun��o testa a cria��o de setores.
	 */
	@Test
	@Order(1)
	void testSetorCreate() {
		SetorService service = new SetorService();
		Response response = null;
		try {		
			response = service.SetorCreate(setor);				
		} catch (Exception e) {
			e.printStackTrace();
			fail (e.toString());
		}
		
		assertEquals(200, response.getStatus(),"Erro interno na criacao do Setor");	
	}
	
	/**
	 * Depois, � testada a atualiza��o do Setor. S�o dois testes, um confere se a opera��o no BD foi conclu�da, 
	 * e o outro confere se o Setor foi realmente atualizado.
	 * O aviso "unchecked" foi suprimido pois se o tipo estiver incorreto o teste falhar�
	 */
	@Test
	@Order(2)
	@SuppressWarnings("unchecked")
	void testSetorUpdate() {
		SetorService service = new SetorService();
		List<Setor> lista = null;
		
		try {
		lista = (List<Setor>) service.SetorRead().getEntity();
		}catch(Exception e) {
			e.printStackTrace();
			fail("Erro interno ao resgatar a lista de Setores");
		}
		
		Setor aux = lista.get(lista.size()-1);		
		Response response = null;
		
		aux.setNome("Financeiro");
		try {
			response = service.SetorUpdate(aux.getId(), aux);
		}catch (Exception e)
		{
			e.printStackTrace();
			fail("Erro interno na atualiza��o do Setor");
		}
			
		setor = (Setor) response.getEntity();
		
		assertEquals("Financeiro", setor.getNome(),"Falha na atualiza��o do Setor");		
	}
	
	/**
	 * O terceiro teste confere se o nosso setor atualizado se encontra na base de dados, 
	 * testando ao mesmo tempo a atualiza��o e a leitura.
	 * O aviso "unchecked" foi suprimido pois se o tipo estiver incorreto o teste falhar�
	 */
	@Test
	@Order(3)
	@SuppressWarnings("unchecked")
	void testSetorRead() {
		SetorService service = new SetorService();
		Response response = null;
		List<Setor> lista = null;
		
		try {
			response = service.SetorRead();			 			
		} catch (Exception e) {
			e.printStackTrace();
			fail (e.toString());
		}
		lista = (List<Setor>) response.getEntity();
		
		assertEquals(setor.getNome(), lista.get(lista.size()-1).getNome(), "Lista retornada n�o contem o funcion�rio de teste");
	}
	
	/**
	 * No teste final, deletamos o setor de teste, para n�o poluir o banco com informa��es imprecisas.
	 * A fun��o n�o est� preparada para deletar setores com funcion�rios, ent�o esse teste n�o foi feito.
	 */
	@Test
	@Order(4)
	void testSetorDelete() {
		SetorService service = new SetorService();
		Response response = null;
		try {
			response = service.SetorDelete(setor.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail (e.toString());
		}
		
		assertEquals(200, response.getStatus(),"Erro interno na exclus�o do Setor");
	}
	
}