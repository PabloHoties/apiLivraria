package br.com.cotiinformatica.domain.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CriarLivroRequestDto {

	@NotBlank(message = "Por favor, informe o título do livro.")
	@Size(min = 5, max = 150, message = "Por favor, informe um título de 5 a 150 caracteres.")
	private String titulo;
	
	@NotBlank(message = "Por favor, informe a descrição do livro.")
	@Size(min = 15, max = 500, message = "Por favor, informe uma descrição de 15 a 500 caracteres.")
	private String descricao;
	
	@NotBlank(message = "Por favor, informe as páginas do livro.")
	@Size(min = 1, max = 5, message = "Por favor, informe as páginas usando de 1 a 5 caracteres.")
	private Integer paginas;
	
	@NotBlank(message = "Por favor, informe o preço do livro.")
	@Size(min = 1, max = 5, message = "Por favor, informe o preço de 1 a 5 caracteres.")
	private Double preco;
	
	@NotNull(message = "Por favor, informe o ID do autor.")
	private UUID autor_id;
	
	@NotNull(message = "Por favor, informe o ID da editora.")
	private UUID editora_id;
}
