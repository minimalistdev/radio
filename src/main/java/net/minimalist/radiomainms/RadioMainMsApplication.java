package net.minimalist.radiomainms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ResourceHttpMessageConverter;

@SpringBootApplication
public class RadioMainMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RadioMainMsApplication.class, args);
	}

	@Bean
	public ResourceHttpMessageConverter customResourceHttpMessageConverter() {
		return new CustomResourceHttpMessageConverter();
	}

}
