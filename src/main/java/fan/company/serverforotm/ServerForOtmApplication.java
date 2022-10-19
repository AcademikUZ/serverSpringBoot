package fan.company.serverforotm;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "server for OTM API", version = "1.0", description = "Server Information"))
public class ServerForOtmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerForOtmApplication.class, args);
    }

}
