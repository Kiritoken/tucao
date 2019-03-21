package com.eli.emailserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.eli.emailserver.dao")
@EnableScheduling
@EnableTransactionManagement
public class EmailServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailServerApplication.class, args);
	}

}
