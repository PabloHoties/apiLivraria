package br.com.cotiinformatica.domain.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class LivroResponseDto {

	private UUID id;
	private String titulo;
	private String descricao;
	private Integer paginas;
	private Double preco;
	private AutorResponseDto autor;
	private EditoraResponseDto editora;
}
