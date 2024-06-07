package br.com.cotiinformatica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.domain.dtos.AtualizarLivroRequestDto;
import br.com.cotiinformatica.domain.dtos.CriarLivroRequestDto;
import br.com.cotiinformatica.domain.dtos.LivroResponseDto;
import br.com.cotiinformatica.domain.entities.Livro;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.class)
public class LivrosTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	static UUID id;
	static UUID autor_id;
	static UUID editora_id;

	@Test
	@Order(1)
	public void criarLivroComSucessoTest() throws Exception {

		Faker faker = new Faker();

		CriarLivroRequestDto dto = new CriarLivroRequestDto();
		dto.setTitulo(faker.book().title());
		dto.setDescricao(faker.lorem().paragraph());
		dto.setPaginas(faker.number().numberBetween(1, 2000));
		dto.setPreco(faker.number().randomDouble(2, 0, 9999));
		dto.setAutor_id(autor_id);
		dto.setEditora_id(editora_id);

		MvcResult result = mockMvc.perform(post("/api/livros/cadastrar").contentType("application/json")
				.content(objectMapper.writeValueAsString(dto))).andExpectAll(status().isCreated()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		LivroResponseDto response = objectMapper.readValue(content, LivroResponseDto.class);

		assertNotNull(response.getId());
		assertEquals(response.getTitulo(), dto.getTitulo());
		assertEquals(response.getDescricao(), dto.getDescricao());
		assertEquals(response.getPaginas(), dto.getPaginas());
		assertEquals(response.getPreco(), dto.getPreco());
		assertEquals(response.getAutor_id(), dto.getAutor_id());
		assertEquals(response.getEditora_id(), dto.getEditora_id());
	}

	@Test
	@Order(2)
	public void criarLivroComIdDoAutorInvalidoTest() throws Exception {

		Faker faker = new Faker();

		CriarLivroRequestDto dto = new CriarLivroRequestDto();
		dto.setTitulo(faker.book().title());
		dto.setDescricao(faker.lorem().paragraph());
		dto.setPaginas(faker.number().numberBetween(1, 2000));
		dto.setPreco(faker.number().randomDouble(2, 0, 9999));
		dto.setAutor_id(UUID.randomUUID());
		dto.setEditora_id(editora_id);

		MvcResult result = mockMvc
				.perform(post("/api/livros/cadastrar").contentType("application/json")
						.content(objectMapper.writeValueAsString(dto)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(""));
	}

	@Test
	@Order(3)
	public void criarLivroComIdDaEditoraInvalidoTest() throws Exception {

		Faker faker = new Faker();

		CriarLivroRequestDto dto = new CriarLivroRequestDto();
		dto.setTitulo(faker.book().title());
		dto.setDescricao(faker.lorem().paragraph());
		dto.setPaginas(faker.number().numberBetween(1, 2000));
		dto.setPreco(faker.number().randomDouble(2, 0, 9999));
		dto.setAutor_id(autor_id);
		dto.setEditora_id(UUID.randomUUID());

		MvcResult result = mockMvc
				.perform(post("/api/livros/cadastrar").contentType("application/json")
						.content(objectMapper.writeValueAsString(dto)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(""));
	}

	@Test
	@Order(4)
	public void criarLivroComDadosInvalidosTest() throws Exception {

		CriarLivroRequestDto dto = new CriarLivroRequestDto();
		dto.setTitulo("");
		dto.setDescricao("");
		dto.setPaginas(null);
		dto.setPreco(null);
		dto.setAutor_id(null);
		dto.setEditora_id(null);

		MvcResult result = mockMvc
				.perform(post("/api/livros/cadastrar").contentType("application/json")
						.content(objectMapper.writeValueAsString(dto)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("título: Por favor, informe o título do livro."));
		assertTrue(content.contains("título: Por favor, informe um título de 5 a 150 caracteres."));
		assertTrue(content.contains("descricao: Por favor, informe a descrição do livro."));
		assertTrue(content.contains("descricao: Por favor, informe uma descrição de 15 a 500 caracteres."));
		assertTrue(content.contains("paginas: Por favor, informe as páginas do livro."));
		assertTrue(content.contains("paginas: Por favor, informe as páginas usando de 1 a 5 caracteres."));
		assertTrue(content.contains("preco: Por favor, informe o preço do livro."));
		assertTrue(content.contains("preco: Por favor, informe o preço de 1 a 5 caracteres."));
		assertTrue(content.contains("autor_id: Por favor, informe o ID do autor."));
		assertTrue(content.contains("editora_id: Por favor, informe o ID da editora."));
	}

	@Test
	@Order(5)
	public void atualizarLivroComSucessoTest() throws Exception {

		Faker faker = new Faker();

		AtualizarLivroRequestDto dto = new AtualizarLivroRequestDto();
		dto.setId(id);
		dto.setTitulo(faker.book().title());
		dto.setDescricao(faker.lorem().paragraph());
		dto.setPaginas(faker.number().numberBetween(1, 2000));
		dto.setPreco(faker.number().randomDouble(2, 0, 9999));
		dto.setAutor_id(autor_id);
		dto.setEditora_id(editora_id);

		MvcResult result = mockMvc.perform(put("/api/livros/atualizar").contentType("application/json")
				.content(objectMapper.writeValueAsString(dto))).andExpectAll(status().isCreated()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		LivroResponseDto response = objectMapper.readValue(content, LivroResponseDto.class);

		assertNotNull(response.getId());
		assertEquals(response.getTitulo(), dto.getTitulo());
		assertEquals(response.getDescricao(), dto.getDescricao());
		assertEquals(response.getPaginas(), dto.getPaginas());
		assertEquals(response.getPreco(), dto.getPreco());
		assertEquals(response.getAutor_id(), dto.getAutor_id());
		assertEquals(response.getEditora_id(), dto.getEditora_id());
	}

	@Test
	@Order(6)
	public void atualizarLivroComIdDoAutorInvalidoTest() throws Exception {

		Faker faker = new Faker();

		AtualizarLivroRequestDto dto = new AtualizarLivroRequestDto();
		dto.setId(id);
		dto.setTitulo(faker.book().title());
		dto.setDescricao(faker.lorem().paragraph());
		dto.setPaginas(faker.number().numberBetween(1, 2000));
		dto.setPreco(faker.number().randomDouble(2, 0, 9999));
		dto.setAutor_id(UUID.randomUUID());
		dto.setEditora_id(editora_id);

		MvcResult result = mockMvc
				.perform(put("/api/livros/atualizar").contentType("application/json")
						.content(objectMapper.writeValueAsString(dto)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(""));
	}

	@Test
	@Order(7)
	public void atualizarLivroComIdDaEditoraInvalidoTest() throws Exception {

		Faker faker = new Faker();

		AtualizarLivroRequestDto dto = new AtualizarLivroRequestDto();
		dto.setId(id);
		dto.setTitulo(faker.book().title());
		dto.setDescricao(faker.lorem().paragraph());
		dto.setPaginas(faker.number().numberBetween(1, 2000));
		dto.setPreco(faker.number().randomDouble(2, 0, 9999));
		dto.setAutor_id(autor_id);
		dto.setEditora_id(UUID.randomUUID());

		MvcResult result = mockMvc
				.perform(put("/api/livros/atualizar").contentType("application/json")
						.content(objectMapper.writeValueAsString(dto)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(""));
	}

	@Test
	@Order(8)
	public void atualizarLivroComDadosInvalidosTest() throws Exception {

		AtualizarLivroRequestDto dto = new AtualizarLivroRequestDto();
		dto.setId(null);
		dto.setTitulo("");
		dto.setDescricao("");
		dto.setPaginas(null);
		dto.setPreco(null);
		dto.setAutor_id(null);
		dto.setEditora_id(null);

		MvcResult result = mockMvc
				.perform(put("/api/livros/atualizar").contentType("application/json")
						.content(objectMapper.writeValueAsString(dto)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("id: Por favor, informe o ID do livro."));
		assertTrue(content.contains("título: Por favor, informe o título do livro."));
		assertTrue(content.contains("título: Por favor, informe um título de 5 a 150 caracteres."));
		assertTrue(content.contains("descricao: Por favor, informe a descrição do livro."));
		assertTrue(content.contains("descricao: Por favor, informe uma descrição de 15 a 500 caracteres."));
		assertTrue(content.contains("paginas: Por favor, informe as páginas do livro."));
		assertTrue(content.contains("paginas: Por favor, informe as páginas usando de 1 a 5 caracteres."));
		assertTrue(content.contains("preco: Por favor, informe o preço do livro."));
		assertTrue(content.contains("preco: Por favor, informe o preço de 1 a 5 caracteres."));
		assertTrue(content.contains("autor_id: Por favor, informe o ID do autor."));
		assertTrue(content.contains("editora_id: Por favor, informe o ID da editora."));
	}

	@Test
	@Order(9)
	public void obterLivroComSucessoTest() throws Exception {

		MvcResult result = mockMvc.perform(get("/api/livros/obter/" + id).contentType("application/json"))
				.andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains((id).toString()));
	}

	@Test
	@Order(10)
	public void obterLivroComIdInvalidoTest() throws Exception {

		MvcResult result = mockMvc
				.perform(get("/api/livros/obter/" + UUID.randomUUID()).contentType("application/json"))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(""));
	}

	@Test
	@Order(11)
	public void consultarLivrosComSucessoTest() throws Exception {

		List<Livro> livros = new ArrayList<Livro>();

		MvcResult result = mockMvc.perform(get("/api/livros/consultar").contentType("application/json")
				.content(objectMapper.writeValueAsString(livros))).andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(id.toString()));
	}

	@Test
	@Order(12)
	public void excluirLivroComSucessoTest() throws Exception {

		MvcResult result = mockMvc
				.perform(delete("/api/livros/excluir/{id}", id.toString()).contentType("application/json"))
				.andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(id.toString()));
	}

	@Test
	@Order(13)
	public void excluirLivroComIdInvalidoTest() throws Exception {

		MvcResult result = mockMvc.perform(
				delete("/api/livros/excluir/{id}", UUID.randomUUID().toString()).contentType("application/json"))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(""));
	}
}
