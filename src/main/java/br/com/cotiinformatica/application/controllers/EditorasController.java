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

import br.com.cotiinformatica.domain.dtos.AtualizarEditoraRequestDto;
import br.com.cotiinformatica.domain.dtos.CriarEditoraRequestDto;
import br.com.cotiinformatica.domain.dtos.EditoraResponseDto;
import br.com.cotiinformatica.domain.interfaces.EditoraDomainService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/editoras")
public class EditorasController {

	@Autowired
	private EditoraDomainService editoraDomainService;

	@PostMapping("cadastrar")
	public ResponseEntity<EditoraResponseDto> criar(@RequestBody @Valid CriarEditoraRequestDto dto) {

		EditoraResponseDto response = editoraDomainService.criarEditora(dto);
		return ResponseEntity.status(201).body(response);
	}

	@PutMapping("atualizar")
	public ResponseEntity<EditoraResponseDto> atualizar(@RequestBody @Valid AtualizarEditoraRequestDto dto) {

		EditoraResponseDto response = editoraDomainService.atualizarEditora(dto);
		return ResponseEntity.status(200).body(response);
	}

	@DeleteMapping("excluir/{id}")
	public ResponseEntity<EditoraResponseDto> excluir(@PathVariable("id") UUID id) {

		EditoraResponseDto response = editoraDomainService.excluirEditora(id);
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("obter/{id}")
	public ResponseEntity<EditoraResponseDto> obter(@PathVariable("id") UUID id) {

		EditoraResponseDto response = editoraDomainService.obterEditora(id);
		return ResponseEntity.status(200).body(response);
	}

	@GetMapping("consultar")
	public ResponseEntity<List<EditoraResponseDto>> consultar() {

		List<EditoraResponseDto> response = editoraDomainService.consultarEditoras();
		return ResponseEntity.status(200).body(response);
	}
}
