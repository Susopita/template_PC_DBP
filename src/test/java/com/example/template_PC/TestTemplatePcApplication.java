package com.example.template_PC;

import org.springframework.boot.SpringApplication;

public class TestTemplatePcApplication {

	public static void main(String[] args) {
		SpringApplication.from(TemplatePcApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
