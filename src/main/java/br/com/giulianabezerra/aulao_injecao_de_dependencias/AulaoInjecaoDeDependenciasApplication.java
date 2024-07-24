package br.com.giulianabezerra.aulao_injecao_de_dependencias;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class AulaoInjecaoDeDependenciasApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(AulaoInjecaoDeDependenciasApplication.class,args);
		
	}
	
	@Bean
	ApplicationRunner runner(MigracaoUsuario migracaoUsuario) {
		return args -> {migracaoUsuario.migrar();
		};
		
	}

}


@Component
class MigracaoUsuario {
	Reader<User> reader;
	Writer<User> writer;
	
	
	public MigracaoUsuario(Reader<User> reader, Writer<User> writer) {
		this.reader = reader;
		this.writer = writer;
	}


	void migrar() {
		// Ler usuarios de A
		List<User> users = reader.read();
		// Escrever usuarios em B
		writer.write(users);
	}
	
}

record User(String email, String username, String password) {
	
}


interface Reader<T> {
	List<T> read();
	
}

interface Writer<T> {
	void write(List<T> itens);
	
}


@Component
class FileReader implements Reader<User>{
 public List<User> read() {
		System.out.println("Lendo usuários do arquivo...");
		return List.of(new User("oemail1","onome1","asenha1"),
				       new User("oemail2","onome2","asenha2")  );
	}
	
	
}

@Component
class BdWriter implements Writer<User>{
	public void write(List<User> users) {
		System.out.println("Escrevendo usuários no banco...");
		System.out.println(users);
		
	}
	
}