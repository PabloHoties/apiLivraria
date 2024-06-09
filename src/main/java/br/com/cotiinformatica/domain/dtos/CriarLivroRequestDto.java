package br.com.cotiinformatica.domain.dtos;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
	
	@NotNull(message = "Por favor, informe as páginas do livro.")
	@Min(value = 1, message = "Por favor, informe um número de páginas acima de 0.")
	@Max(value = 10000, message = "Por favor, informe um número de páginas abaixo de 10.000.")
	private Integer paginas;
	
	@NotNull(message = "Por favor, informe o preço do livro.")
	@Min(value = 0, message = "Por favor, informe um preço usando um número natural.")
	@Max(value = 99999, message = "Por favor, informe um preço abaixo de R$ 99.999.")
	private Double preco;
	
	@NotNull(message = "Por favor, informe o ID do autor.")
	private UUID autor_id;
	
	@NotNull(message = "Por favor, informe o ID da editora.")
	private UUID editora_id;
}
