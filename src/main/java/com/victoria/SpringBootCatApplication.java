package com.victoria;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.victoria.*.mapper"})
public class SpringBootCatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCatApplication.class, args);
	}

}
