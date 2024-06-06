package br.com.cotiinformatica.domain.dtos;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class AutorResponseDto {

	private UUID id;
	private String nome;
	private String biografia;
	private Date dataNascimento;
}
