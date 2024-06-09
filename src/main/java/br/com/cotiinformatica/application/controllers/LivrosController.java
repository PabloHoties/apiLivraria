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

import br.com.cotiinformatica.domain.dtos.AtualizarLivroRequestDto;
import br.com.cotiinformatica.domain.dtos.CriarLivroRequestDto;
import br.com.cotiinformatica.domain.dtos.LivroResponseDto;
import br.com.cotiinformatica.domain.interfaces.LivroDomainService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/livros")
public class LivrosController {

	@Autowired
	LivroDomainService livroDomainService;

	@PostMapping("cadastrar")
	public ResponseEntity<LivroResponseDto> criar(@RequestBody @Valid CriarLivroRequestDto dto) {

		LivroResponseDto response = livroDomainService.criarLivro(dto);
		return ResponseEntity.status(201).body(response);
	}

	@PutMapping("atualizar")
	public ResponseEntity<LivroResponseDto> atualizar(@RequestBody @Valid AtualizarLivroRequestDto dto) {

		LivroResponseDto response = livroDomainService.atualizarLivro(dto);
		return ResponseEntity.status(200).body(response);
	}

	@DeleteMapping("excluir/{id}")
	public ResponseEntity<LivroResponseDto> excluir(@PathVariable("id") UUID id) {

		LivroResponseDto response = livroDomainService.excluirLivro(id);
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("obter/{id}")
	public ResponseEntity<LivroResponseDto> obter(@PathVariable("id") UUID id) {

		LivroResponseDto response = livroDomainService.obterLivro(id);
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("consultar")
	public ResponseEntity<List<LivroResponseDto>> consultar() {

		List<LivroResponseDto> response = livroDomainService.consultarLivros();
		return ResponseEntity.status(200).body(response);
	}
}
