package com.maxisoft.individualsapi;

import org.springframework.boot.SpringApplication;

public class TestMaxisoftIndividualsApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
