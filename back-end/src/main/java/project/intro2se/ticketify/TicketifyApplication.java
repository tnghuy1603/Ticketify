package project.intro2se.ticketify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TicketifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketifyApplication.class, args);
	}

}
