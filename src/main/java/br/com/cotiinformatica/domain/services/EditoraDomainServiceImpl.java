package br.com.cotiinformatica.domain.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.cotiinformatica.domain.dtos.AtualizarEditoraRequestDto;
import br.com.cotiinformatica.domain.dtos.CriarEditoraRequestDto;
import br.com.cotiinformatica.domain.dtos.EditoraResponseDto;
import br.com.cotiinformatica.domain.entities.Editora;
import br.com.cotiinformatica.domain.interfaces.EditoraDomainService;
import br.com.cotiinformatica.infrastructure.repositories.EditoraRepository;

public class EditoraDomainServiceImpl implements EditoraDomainService {

	@Autowired
	EditoraRepository editoraRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public EditoraResponseDto criarEditora(CriarEditoraRequestDto dto) {

		Editora editora = modelMapper.map(dto, Editora.class);
		editora.setId(UUID.randomUUID());

		editoraRepository.save(editora);

		EditoraResponseDto response = modelMapper.map(editora, EditoraResponseDto.class);
		return response;
	}

	@Override
	public EditoraResponseDto atualizarEditora(AtualizarEditoraRequestDto dto) {

		if (editoraRepository.findById(dto.getId()).isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar uma editora com o ID informado.");

		Editora editora = modelMapper.map(dto, Editora.class);

		editoraRepository.save(editora);

		EditoraResponseDto response = modelMapper.map(editora, EditoraResponseDto.class);
		return response;
	}

	@Override
	public EditoraResponseDto excluirEditora(UUID id) {

		Optional<Editora> editora = editoraRepository.findById(id);

		if (editora.isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar uma editora com o ID informado.");

		editoraRepository.delete(editora.get());

		EditoraResponseDto response = modelMapper.map(editora, EditoraResponseDto.class);
		return response;
	}

	@Override
	public EditoraResponseDto obterEditora(UUID id) {

		Optional<Editora> editora = editoraRepository.findById(id);

		if (editora.isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar uma editora com o ID informado.");

		EditoraResponseDto response = modelMapper.map(editora, EditoraResponseDto.class);
		return response;
	}

	@Override
	public List<EditoraResponseDto> consultarEditoras() {

		List<Editora> editoras = editoraRepository.findAll();

		List<EditoraResponseDto> response = modelMapper.map(editoras, new TypeToken<List<EditoraResponseDto>>() {
		}.getType());
		return response;
	}

}
