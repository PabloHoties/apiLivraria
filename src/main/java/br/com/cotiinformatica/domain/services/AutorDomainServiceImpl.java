package br.com.cotiinformatica.domain.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.domain.dtos.AtualizarAutorRequestDto;
import br.com.cotiinformatica.domain.dtos.AutorResponseDto;
import br.com.cotiinformatica.domain.dtos.CriarAutorRequestDto;
import br.com.cotiinformatica.domain.entities.Autor;
import br.com.cotiinformatica.domain.interfaces.AutorDomainService;
import br.com.cotiinformatica.infrastructure.repositories.AutorRepository;

@Service
public class AutorDomainServiceImpl implements AutorDomainService {

	@Autowired
	AutorRepository autorRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public AutorResponseDto criarAutor(CriarAutorRequestDto dto) {

		Autor autor = modelMapper.map(dto, Autor.class);
		autor.setId(UUID.randomUUID());

		autorRepository.save(autor);

		AutorResponseDto response = modelMapper.map(autor, AutorResponseDto.class);
		return response;
	}

	@Override
	public AutorResponseDto atualizarAutor(AtualizarAutorRequestDto dto) {

		if (autorRepository.findById(dto.getId()).isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar um autor com o ID informado.");

		Autor autor = modelMapper.map(dto, Autor.class);

		autorRepository.save(autor);

		AutorResponseDto response = modelMapper.map(autor, AutorResponseDto.class);
		return response;
	}

	@Override
	public AutorResponseDto excluirAutor(UUID id) {

		Optional<Autor> autor = autorRepository.findById(id);

		if (autor.isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar um autor com o ID informado.");

		autorRepository.delete(autor.get());

		AutorResponseDto response = modelMapper.map(autor, AutorResponseDto.class);
		return response;
	}

	@Override
	public AutorResponseDto obterAutor(UUID id) {

		Optional<Autor> autor = autorRepository.findById(id);

		if (autor.isEmpty())
			throw new IllegalArgumentException("Não foi possível encontrar um autor com o ID informado.");

		AutorResponseDto response = modelMapper.map(autor, AutorResponseDto.class);
		return response;
	}

	@Override
	public List<AutorResponseDto> consultarAutores() {

		List<Autor> autores = autorRepository.findAll();

		List<AutorResponseDto> response = modelMapper.map(autores, new TypeToken<List<AutorResponseDto>>() {
		}.getType());
		return response;
	}
}
