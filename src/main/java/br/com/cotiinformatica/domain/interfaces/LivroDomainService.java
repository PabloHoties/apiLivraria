package br.com.cotiinformatica.domain.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.domain.dtos.AtualizarLivroRequestDto;
import br.com.cotiinformatica.domain.dtos.CriarLivroRequestDto;
import br.com.cotiinformatica.domain.dtos.LivroResponseDto;

public interface LivroDomainService {

	LivroResponseDto criarLivro(CriarLivroRequestDto dto);
	
	LivroResponseDto atualizarLivro(AtualizarLivroRequestDto dto);
	
	LivroResponseDto excluirLivro(UUID id);
	
	LivroResponseDto obterLivro(UUID id);
	
	List<LivroResponseDto> consultarLivros();
}
