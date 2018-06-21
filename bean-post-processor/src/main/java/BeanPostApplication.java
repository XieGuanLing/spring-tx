import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gl on 2017/9/18.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.ws"
//        没作用？
//        , excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class) }
)
public class BeanPostApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeanPostApplication.class, args);
    }

}