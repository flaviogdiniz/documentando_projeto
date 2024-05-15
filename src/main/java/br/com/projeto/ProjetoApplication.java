package br.com.projeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Documentando APIs",
		version = "1.0",
		description = "Documentando uma API básica de gerenciamento de pessoas",
		contact = @Contact(name = "Flavio", email = "flaviogdiniz@gmail.com", url = "https://github.com/flaviogdiniz")
	)
)
public class ProjetoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoApplication.class, args);
	}

}
