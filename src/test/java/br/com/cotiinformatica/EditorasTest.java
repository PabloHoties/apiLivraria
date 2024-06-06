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

import br.com.cotiinformatica.domain.dtos.AtualizarEditoraRequestDto;
import br.com.cotiinformatica.domain.dtos.CriarEditoraRequestDto;
import br.com.cotiinformatica.domain.dtos.EditoraResponseDto;
import br.com.cotiinformatica.domain.entities.Editora;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EditorasTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	static UUID id;

	@Test
	@Order(1)
	public void criarEditoraComSucessoTest() throws Exception {

		Faker faker = new Faker();

		CriarEditoraRequestDto dto = new CriarEditoraRequestDto();
		dto.setNome(faker.name().fullName());

		MvcResult result = mockMvc.perform(post("/api/editoras/cadastrar").contentType("application/json")
				.content(objectMapper.writeValueAsString(dto))).andExpectAll(status().isCreated()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		EditoraResponseDto response = objectMapper.readValue(content, EditoraResponseDto.class);

		assertNotNull(response.getId());
		assertEquals(response.getNome(), dto.getNome());
	}

	@Test
	@Order(2)
	public void criarEditoraComDadosInvalidosTest() throws Exception {

		CriarEditoraRequestDto dto = new CriarEditoraRequestDto();
		dto.setNome("");

		MvcResult result = mockMvc
				.perform(post("/api/editoras/cadastrar").contentType("application/json")
						.content(objectMapper.writeValueAsString(dto)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("nome: Por favor, informe o nome do autor."));
		assertTrue(content.contains("nome: Por favor, informe um nome de 8 a 150 caracteres."));
	}

	@Test
	@Order(3)
	public void atualizarEditoraComSucessoTest() throws Exception {

		Faker faker = new Faker();

		AtualizarEditoraRequestDto dto = new AtualizarEditoraRequestDto();
		dto.setId(id);
		dto.setNome(faker.name().fullName());

		MvcResult result = mockMvc.perform(put("/api/editoras/atualizar").contentType("application/json")
				.content(objectMapper.writeValueAsString(dto))).andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		EditoraResponseDto response = objectMapper.readValue(content, EditoraResponseDto.class);

		assertNotNull(response.getId());
		assertEquals(response.getNome(), dto.getNome());
	}

	@Test
	@Order(4)
	public void atualizarEditoraComIdInvalidoTest() throws Exception {

		Faker faker = new Faker();

		AtualizarEditoraRequestDto dto = new AtualizarEditoraRequestDto();
		dto.setId(UUID.randomUUID());
		dto.setNome(faker.name().fullName());

		MvcResult result = mockMvc
				.perform(put("/api/editoras/atualizar").contentType("application/json")
						.content(objectMapper.writeValueAsString(dto)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(""));
	}

	@Test
	@Order(5)
	public void atualizarEditoraComDadosInvalidosTest() throws Exception {

		AtualizarEditoraRequestDto dto = new AtualizarEditoraRequestDto();
		dto.setId(null);
		dto.setNome("");

		MvcResult result = mockMvc
				.perform(put("/api/editoras/atualizar").contentType("application/json")
						.content(objectMapper.writeValueAsString(dto)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("id: Por favor, informe o ID da editora."));
		assertTrue(content.contains("nome: Por favor, informe o nome do autor."));
		assertTrue(content.contains("nome: Por favor, informe um nome de 8 a 150 caracteres."));
	}

	@Test
	@Order(6)
	public void obterEditoraComSucessoTest() throws Exception {

		MvcResult result = mockMvc.perform(get("/api/editoras/obter/" + id).contentType("application/json"))
				.andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains((id).toString()));
	}

	@Test
	@Order(7)
	public void obterEditoraComIdInvalidoTest() throws Exception {

		MvcResult result = mockMvc
				.perform(get(("/api/editoras/obter/") + UUID.randomUUID()).contentType("application/json"))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(""));
	}

	@Test
	@Order(8)
	public void consultarEditorasComSucessoTest() throws Exception {

		List<Editora> editoras = new ArrayList<Editora>();

		MvcResult result = mockMvc.perform(get("/api/editoras/consultar").contentType("application/json")
				.content(objectMapper.writeValueAsString(editoras))).andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains((id.toString())));
	}

	@Test
	@Order(9)
	public void excluirEditoraComSucessoTest() throws Exception {

		MvcResult result = mockMvc
				.perform(delete("/api/editoras/excluir/{id}", id.toString()).contentType("application/json"))
				.andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains((id.toString())));
	}

	@Test
	@Order(10)
	public void excluirEditoraComIdInvalidoTest() throws Exception {

		MvcResult result = mockMvc
				.perform(delete("/api/editoras/excluir/{id}", id.toString()).contentType("application/json"))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(""));
	}
}
