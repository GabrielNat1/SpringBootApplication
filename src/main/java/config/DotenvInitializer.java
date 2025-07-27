package config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvInitializer {
    public static Dotenv dotenv;

   @PostConstruct
    public static void init(){
       dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

       System.out.println("Dotenv loaded: " + dotenv.get("ENV_VAR_NAME"));
   }
}
