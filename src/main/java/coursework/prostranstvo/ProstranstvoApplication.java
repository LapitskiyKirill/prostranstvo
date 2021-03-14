package coursework.prostranstvo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ProstranstvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProstranstvoApplication.class, args);
	}

}
