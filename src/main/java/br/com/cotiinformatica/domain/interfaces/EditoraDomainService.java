package br.com.cotiinformatica.domain.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.domain.dtos.AtualizarEditoraRequestDto;
import br.com.cotiinformatica.domain.dtos.CriarEditoraRequestDto;
import br.com.cotiinformatica.domain.dtos.EditoraResponseDto;

public interface EditoraDomainService {

	EditoraResponseDto criarEditora(CriarEditoraRequestDto dto);

	EditoraResponseDto atualizarEditora(AtualizarEditoraRequestDto dto);

	EditoraResponseDto excluirEditora(UUID id);

	EditoraResponseDto obterEditora(UUID id);

	List<EditoraResponseDto> consultarEditoras();
}
