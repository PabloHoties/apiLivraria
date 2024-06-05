package br.com.cotiinformatica.domain.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_livro")
public class Livro {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "titulo", length = 150, nullable = false)
	private String titulo;
	
	@Column(name = "descricao", length = 500, nullable = false)
	private String descricao;
	
	@Column(name = "paginas", length = 5)
	private Integer paginas;
	
	@Column(name = "preco", length = 5)
	private Double preco;
	
	@ManyToOne
	@JoinColumn(name = "autor_id", nullable = false)
	private Autor autor;
	
	@ManyToOne
	@JoinColumn(name = "editora_id", nullable = false)
	private Editora editora;
}
