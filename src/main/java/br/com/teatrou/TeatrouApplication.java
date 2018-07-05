package br.com.teatrou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.teatrou.config.property.TeatrouApiProperty;


@SpringBootApplication
@EnableConfigurationProperties(TeatrouApiProperty.class)
public class TeatrouApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeatrouApplication.class, args);
	}
}
