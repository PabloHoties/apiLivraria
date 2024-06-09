package br.com.cotiinformatica.infrastructure.data;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import br.com.cotiinformatica.domain.entities.Autor;
import br.com.cotiinformatica.domain.entities.Editora;
import br.com.cotiinformatica.infrastructure.repositories.AutorRepository;
import br.com.cotiinformatica.infrastructure.repositories.EditoraRepository;

@Component
public class LoadData implements ApplicationRunner {

	@Autowired
	AutorRepository autorRepository;

	@Autowired
	EditoraRepository editoraRepository;

	static UUID autor_id = UUID.fromString("3ad8c507-1b08-42ee-9924-f55e0261d7ef");
	static UUID editora_id = UUID.fromString("b6265474-25d8-4a87-a0a7-3e55dba9e77a");

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Faker faker = new Faker();

		if (autorRepository.findById(autor_id).isEmpty()) {

			Autor autor = new Autor();
			autor.setId(autor_id);
			autor.setNome("Autor Exemplo");
			autor.setBiografia(faker.lorem().paragraph());
			autor.setDataNascimento(new Date());

			autorRepository.save(autor);
		}

		if (editoraRepository.findById(editora_id).isEmpty()) {

			Editora editora = new Editora();
			editora.setId(editora_id);
			editora.setNome("Editora Exemplo");

			editoraRepository.save(editora);
		}

	}

}
