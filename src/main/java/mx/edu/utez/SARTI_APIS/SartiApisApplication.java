package mx.edu.utez.SARTI_APIS;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class SartiApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SartiApisApplication.class, args);
	}

}
