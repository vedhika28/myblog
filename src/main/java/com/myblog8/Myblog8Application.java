package com.myblog8;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication//this annotation also make congiure class and projects starts from here.
public class Myblog8Application {

	public static void main(String[] args) {
		SpringApplication.run(Myblog8Application.class, args);
	}
	// when we give @bean ,it isloaded automatically in ioc,now if we do @autowired spring ioc knowsthat here is a method which is returning a modelmapperobject
	//it takes the object and injects into the reference variable
	//we use bean annotation for external library thus spring ioc knows which object to create and inject into reference vafriable.


	//flow confusion
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();

	}
}