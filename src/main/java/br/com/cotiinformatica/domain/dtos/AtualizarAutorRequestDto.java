package br.com.cotiinformatica.domain.dtos;

import java.util.Date;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AtualizarAutorRequestDto {

	@NotNull(message = "Por favor, informe o ID do autor.")
	private UUID id;
	
	@NotBlank(message = "Por favor, informe o nome do autor.")
	@Size(min = 8, max = 150, message = "Por favor, informe um nome de 8 a 150 caracteres.")
	private String nome;
	
	@NotBlank(message = "Por favor, informe a biografia do autor.")
	@Size(min = 50, max = 1000, message = "Por favor, informe uma biografia de 8 a 150 caracteres.")
	private String biografia;
	
	@NotNull(message = "Por faovr, informe a data de nascimento do autor.")
	private Date dataNascimento;
}
