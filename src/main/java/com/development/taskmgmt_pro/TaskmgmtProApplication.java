package com.development.taskmgmt_pro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.development.taskmgmt_pro.model")
@EnableJpaRepositories("com.development.taskmgmt_pro.repository")
@SpringBootApplication
public class TaskmgmtProApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmgmtProApplication.class, args);
	}

}
