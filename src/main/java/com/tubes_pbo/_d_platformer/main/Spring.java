package com.tubes_pbo._d_platformer.main;

import java.awt.EventQueue;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.tubes_pbo._d_platformer.main")
@EnableAutoConfiguration
public class Spring {
	public static void main(String[] args) {

		ConfigurableApplicationContext context = new SpringApplicationBuilder(Spring.class).headless(false)
				.run(args);

		EventQueue.invokeLater(() -> {
			@SuppressWarnings("unused")
			Game window = context.getBean(Game.class);
		});
	}
}
