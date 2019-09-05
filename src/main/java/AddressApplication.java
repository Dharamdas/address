import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "address.app")
public class AddressApplication {

    public static void main(String...args)
    {
        SpringApplication.run(AddressApplication.class);
    }

}
