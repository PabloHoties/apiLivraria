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
import java.util.Date;
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

import br.com.cotiinformatica.domain.dtos.AtualizarAutorRequestDto;
import br.com.cotiinformatica.domain.dtos.AutorResponseDto;
import br.com.cotiinformatica.domain.dtos.CriarAutorRequestDto;
import br.com.cotiinformatica.domain.entities.Autor;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AutoresTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	static UUID id;

	@Test
	@Order(1)
	public void criarAutorComSucessoTest() throws Exception {

		Faker faker = new Faker();

		CriarAutorRequestDto dto = new CriarAutorRequestDto();
		dto.setNome(faker.name().fullName());
		dto.setBiografia(faker.lorem().paragraph());
		dto.setDataNascimento(new Date());

		MvcResult result = mockMvc.perform(post("/api/autores/cadastrar").contentType("application/json")
				.content(objectMapper.writeValueAsString(dto))).andExpectAll(status().isCreated()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		AutorResponseDto response = objectMapper.readValue(content, AutorResponseDto.class);

		assertNotNull(response.getId());
		assertEquals(response.getNome(), dto.getNome());
		assertEquals(response.getBiografia(), dto.getBiografia());
		assertEquals(response.getDataNascimento(), dto.getDataNascimento());
		
		id = response.getId();
	}

	@Test
	@Order(2)
	public void criarAutorComDadosInvalidosTest() throws Exception {

		CriarAutorRequestDto dto = new CriarAutorRequestDto();
		dto.setNome("");
		dto.setBiografia("");
		dto.setDataNascimento(null);

		MvcResult result = mockMvc.perform(post("/api/autores/cadastrar").contentType("application/json")
				.content(objectMapper.writeValueAsString(dto))).andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("nome: Por favor, informe o nome do autor."));
		assertTrue(content.contains("nome: Por favor, informe um nome de 8 a 150 caracteres."));
		assertTrue(content.contains("biografia: Por favor, informe a biografia do autor."));
		assertTrue(content.contains("biografia: Por favor, informe uma biografia de 8 a 150 caracteres."));
		assertTrue(content.contains("dataNascimento: Por favor, informe a data de nascimento do autor."));
	}

	@Test
	@Order(3)
	public void atualizarAutorComSucessoTest() throws Exception {

		Faker faker = new Faker();

		AtualizarAutorRequestDto dto = new AtualizarAutorRequestDto();
		dto.setId(id);
		dto.setNome(faker.name().fullName());
		dto.setBiografia(faker.lorem().paragraph());
		dto.setDataNascimento(new Date());

		MvcResult result = mockMvc.perform(put("/api/autores/atualizar").contentType("application/json")
				.content(objectMapper.writeValueAsString(dto))).andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		AutorResponseDto response = objectMapper.readValue(content, AutorResponseDto.class);

		assertNotNull(response.getId());
		assertEquals(response.getNome(), dto.getNome());
		assertEquals(response.getBiografia(), dto.getBiografia());
		assertEquals(response.getDataNascimento(), dto.getDataNascimento());
	}

	@Test
	@Order(4)
	public void atualizarAutorComIdInvalidoTest() throws Exception {

		Faker faker = new Faker();

		AtualizarAutorRequestDto dto = new AtualizarAutorRequestDto();
		dto.setId(UUID.randomUUID());
		dto.setNome(faker.name().fullName());
		dto.setBiografia(faker.lorem().paragraph());
		dto.setDataNascimento(new Date());

		MvcResult result = mockMvc
				.perform(put("/api/autores/atualizar").contentType("application/json")
						.content(objectMapper.writeValueAsString(dto)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(""));
	}

	@Test
	@Order(5)
	public void atualizarAutorComDadosInvalidosTest() throws Exception {

		AtualizarAutorRequestDto dto = new AtualizarAutorRequestDto();
		dto.setId(null);
		dto.setNome("");
		dto.setBiografia("");
		dto.setDataNascimento(null);

		MvcResult result = mockMvc
				.perform(put("/api/autores/atualizar").contentType("application/json")
						.content(objectMapper.writeValueAsString(dto)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("id: Por favor, informe o ID do autor."));
		assertTrue(content.contains("nome: Por favor, informe o nome do autor."));
		assertTrue(content.contains("nome: Por favor, informe um nome de 8 a 150 caracteres."));
		assertTrue(content.contains("biografia: Por favor, informe a biografia do autor."));
		assertTrue(content.contains("biografia: Por favor, informe uma biografia de 8 a 150 caracteres."));
		assertTrue(content.contains("dataNascimento: Por favor, informe a data de nascimento do autor."));
	}

	@Test
	@Order(6)
	public void obterAutorComSucessoTest() throws Exception {

		MvcResult result = mockMvc.perform(get("/api/autores/obter/" + id).contentType("application/json"))
				.andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains((id).toString()));
	}

	@Test
	@Order(7)
	public void obterAutorComIdInvalidoTest() throws Exception {

		MvcResult result = mockMvc
				.perform(get("/api/autores/obter/" + UUID.randomUUID()).contentType("application/json"))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(""));
	}

	@Test
	@Order(8)
	public void consultarAutoresComSucessoTest() throws Exception {

		List<Autor> autores = new ArrayList<Autor>();

		MvcResult result = mockMvc.perform(get("/api/autores/consultar").contentType("application/json")
				.content(objectMapper.writeValueAsString(autores))).andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(id.toString()));
	}

	@Test
	@Order(9)
	public void excluirAutorComSucessoTest() throws Exception {

		MvcResult result = mockMvc
				.perform(delete("/api/autores/excluir/{id}", id.toString()).contentType("application/json"))
				.andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(id.toString()));
	}

	@Test
	@Order(10)
	public void excluirAutorComIdInvalidoTest() throws Exception {

		MvcResult result = mockMvc.perform(
				delete("/api/autores/excluir/{id}", UUID.randomUUID().toString()).contentType("application/json"))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(""));
	}
}
