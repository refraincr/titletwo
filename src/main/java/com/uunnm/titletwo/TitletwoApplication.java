package com.uunnm.titletwo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.uunnm.titletwo.business.auth.mapper")
public class TitletwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TitletwoApplication.class, args);
	}

}
