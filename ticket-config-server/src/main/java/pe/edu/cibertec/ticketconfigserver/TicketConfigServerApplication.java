package pe.edu.cibertec.ticketconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class TicketConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketConfigServerApplication.class, args);
    }

}
