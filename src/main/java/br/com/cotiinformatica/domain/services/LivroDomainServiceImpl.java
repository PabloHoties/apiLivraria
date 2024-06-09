package br.com.cotiinformatica.domain.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.domain.dtos.AtualizarLivroRequestDto;
import br.com.cotiinformatica.domain.dtos.CriarLivroRequestDto;
import br.com.cotiinformatica.domain.dtos.LivroResponseDto;
import br.com.cotiinformatica.domain.entities.Livro;
import br.com.cotiinformatica.domain.interfaces.LivroDomainService;
import br.com.cotiinformatica.infrastructure.repositories.AutorRepository;
import br.com.cotiinformatica.infrastructure.repositories.EditoraRepository;
import br.com.cotiinformatica.infrastructure.repositories.LivroRepository;

@Service
public class LivroDomainServiceImpl implements LivroDomainService {

	@Autowired
	LivroRepository livroRepository;

	@Autowired
	AutorRepository autorRepository;

	@Autowired
	EditoraRepository editoraRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public LivroResponseDto criarLivro(CriarLivroRequestDto dto) {

		if (autorRepository.findById(dto.getAutor_id()).isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar um autor com o ID informado.");

		if (editoraRepository.findById(dto.getEditora_id()).isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar uma editora com o ID informado.");

		Livro livro = modelMapper.map(dto, Livro.class);

		livroRepository.save(livro);

		LivroResponseDto response = modelMapper.map(livro, LivroResponseDto.class);
		return response;
	}

	@Override
	public LivroResponseDto atualizarLivro(AtualizarLivroRequestDto dto) {

		if (autorRepository.findById(dto.getAutor_id()).isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar um autor com o ID informado.");

		if (editoraRepository.findById(dto.getEditora_id()).isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar uma editora com o ID informado.");

		if (livroRepository.findById(dto.getId()).isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar um livro com o ID informado.");

		Livro livro = modelMapper.map(dto, Livro.class);

		livroRepository.save(livro);

		LivroResponseDto response = modelMapper.map(livro, LivroResponseDto.class);
		return response;
	}

	@Override
	public LivroResponseDto excluirLivro(UUID id) {

		Optional<Livro> livro = livroRepository.findById(id);

		if (livro.isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar um livro com o ID informado.");

		livroRepository.delete(livro.get());

		LivroResponseDto response = modelMapper.map(livro, LivroResponseDto.class);
		return response;
	}

	@Override
	public LivroResponseDto obterLivro(UUID id) {

		Optional<Livro> livro = livroRepository.findById(id);

		if (livro.isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar um livro com o ID informado.");

		LivroResponseDto response = modelMapper.map(livro, LivroResponseDto.class);
		return response;
	}

	@Override
	public List<LivroResponseDto> consultarLivros() {

		List<Livro> livro = livroRepository.findAll();

		List<LivroResponseDto> response = modelMapper.map(livro, new TypeToken<List<LivroResponseDto>>() {
		}.getType());
		return response;
	}

}
