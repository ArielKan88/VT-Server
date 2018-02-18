package com.processver.view;

import com.processver.controller.MyProcessExaminer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@SpringBootApplication
@ComponentScan(basePackageClasses = MyProcessExaminer.class)
public class ProcessverApplication {
	@Bean
	public View jsonTemplate() {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.setPrettyPrint(true);
		return view;
	}

	@Bean
	public ViewResolver viewResolver() {
		return new BeanNameViewResolver();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProcessverApplication.class, args);
	}
}
