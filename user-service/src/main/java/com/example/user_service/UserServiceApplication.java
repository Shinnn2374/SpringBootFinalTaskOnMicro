package com.example.user_service;

import com.example.user_service.dao.UserRepository;
import com.example.user_service.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			userRepository.save(User.builder()
					.username("admin")
					.email("admin@example.com")
					.password("123456")
					.build());
		};
	}

}
