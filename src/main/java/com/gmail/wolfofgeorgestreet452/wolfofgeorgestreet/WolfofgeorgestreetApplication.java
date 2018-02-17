package com.gmail.wolfofgeorgestreet452.wolfofgeorgestreet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class WolfofgeorgestreetApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WolfofgeorgestreetApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WolfofgeorgestreetApplication.class, args);

	}
}
