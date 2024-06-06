package br.com.cotiinformatica.domain.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AtualizarEditoraRequestDto {

	@NotNull(message = "Por favor, informe o ID da editora.")
	private UUID id;

	@NotBlank(message = "Por favor, informe o nome do autor.")
	@Size(min = 8, max = 150, message = "Por favor, informe um nome de 8 a 150 caracteres.")
	private String nome;
}
