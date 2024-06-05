package br.com.cotiinformatica.domain.entities;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_autor")
public class Autor {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "nome", length = 150, nullable = false)
	private String nome;
	
	@Column(name = "biografia", length = 1000, nullable = false)
	private String biografia;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "datanascimento", nullable = false)
	private Date dataNascimento;
	
	@OneToMany(mappedBy = "autor")
	private List<Livro> livros;
}
