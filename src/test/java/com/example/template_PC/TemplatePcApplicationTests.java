package com.example.template_PC;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TemplatePcApplicationTests {

	@Test
	void contextLoads() {
	}

}
