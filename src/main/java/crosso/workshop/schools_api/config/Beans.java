package crosso.workshop.schools_api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    public ModelMapper provideModelMapper() {
        return new ModelMapper();
    }

}
