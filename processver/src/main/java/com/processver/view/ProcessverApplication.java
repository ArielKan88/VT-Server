package com.processver.view;

import com.processver.controller.MyProcessExaminer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = MyProcessExaminer.class)
public class ProcessverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessverApplication.class, args);
	}
}
