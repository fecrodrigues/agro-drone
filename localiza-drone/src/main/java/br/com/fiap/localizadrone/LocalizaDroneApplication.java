package br.com.fiap.localizadrone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.fiap")
public class LocalizaDroneApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalizaDroneApplication.class, args);
	}

}
