package com.uunnm.titletwo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TitletwoApplicationTests {
	@Value("${jwt.secret}")
	private String secret_key;

	@Test
	void contextLoads() {
		System.out.println(secret_key);
	}

}
