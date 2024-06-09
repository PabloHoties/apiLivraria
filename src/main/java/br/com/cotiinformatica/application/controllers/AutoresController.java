package br.com.cotiinformatica.application.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.domain.dtos.AtualizarAutorRequestDto;
import br.com.cotiinformatica.domain.dtos.AutorResponseDto;
import br.com.cotiinformatica.domain.dtos.CriarAutorRequestDto;
import br.com.cotiinformatica.domain.interfaces.AutorDomainService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/autores")
public class AutoresController {

	@Autowired
	AutorDomainService autorDomainService;

	@PostMapping("cadastrar")
	public ResponseEntity<AutorResponseDto> criar(@RequestBody @Valid CriarAutorRequestDto dto) {

		AutorResponseDto response = autorDomainService.criarAutor(dto);
		return ResponseEntity.status(201).body(response);
	}

	@PutMapping("atualizar")
	public ResponseEntity<AutorResponseDto> atualizar(@RequestBody @Valid AtualizarAutorRequestDto dto) {

		AutorResponseDto response = autorDomainService.atualizarAutor(dto);
		return ResponseEntity.status(200).body(response);
	}

	@DeleteMapping("excluir/{id}")
	public ResponseEntity<AutorResponseDto> excluir(@PathVariable("id") UUID id) {

		AutorResponseDto response = autorDomainService.excluirAutor(id);
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("obter/{id}")
	public ResponseEntity<AutorResponseDto> obter(@PathVariable("id") UUID id) {

		AutorResponseDto response = autorDomainService.obterAutor(id);
		return ResponseEntity.status(200).body(response);
	}
	
	@GetMapping("consultar")
	public ResponseEntity<List<AutorResponseDto>> consultar() {
		
		List<AutorResponseDto> response = autorDomainService.consultarAutores();
		return ResponseEntity.status(200).body(response);
	}
}
