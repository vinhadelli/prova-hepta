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
	 * Definição da classe a ser testada e do funcionário que será utilizado como teste.
	 * @throws Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		setor = new Setor();
		setor.setNome("Setor de Teste");
	}
	
	/**
	 * Essa função testa a criação de setores.
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
	 * Depois, é testada a atualização do Setor. São dois testes, um confere se a operação no BD foi concluída, 
	 * e o outro confere se o Setor foi realmente atualizado.
	 * O aviso "unchecked" foi suprimido pois se o tipo estiver incorreto o teste falhará
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
			fail("Erro interno na atualização do Setor");
		}
			
		setor = (Setor) response.getEntity();
		
		assertEquals("Financeiro", setor.getNome(),"Falha na atualização do Setor");		
	}
	
	/**
	 * O terceiro teste confere se o nosso setor atualizado se encontra na base de dados, 
	 * testando ao mesmo tempo a atualização e a leitura.
	 * O aviso "unchecked" foi suprimido pois se o tipo estiver incorreto o teste falhará
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
		
		assertEquals(setor.getNome(), lista.get(lista.size()-1).getNome(), "Lista retornada não contem o funcionário de teste");
	}
	
	/**
	 * No teste final, deletamos o setor de teste, para não poluir o banco com informações imprecisas.
	 * A função não está preparada para deletar setores com funcionários, então esse teste não foi feito.
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
		
		assertEquals(200, response.getStatus(),"Erro interno na exclusão do Setor");
	}
	
}