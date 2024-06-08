package br.com.cotiinformatica.domain.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.domain.dtos.AtualizarAutorRequestDto;
import br.com.cotiinformatica.domain.dtos.AutorResponseDto;
import br.com.cotiinformatica.domain.dtos.CriarAutorRequestDto;

public interface AutorDomainService {

	AutorResponseDto criarAutor(CriarAutorRequestDto dto);
	
	AutorResponseDto atualizarAutor(AtualizarAutorRequestDto dto);
	
	AutorResponseDto excluirAutor(UUID id);
	
	AutorResponseDto obterAutor(UUID id);
	
	List<AutorResponseDto> consultarAutores();
}
