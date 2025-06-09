package com.example.attendance;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.attendance.model.User;
import com.example.attendance.repository.UserRepository;

@SpringBootApplication
public class AttendanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendanceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initData(UserRepository userRepo) {
		return args -> {
			if (userRepo.findByUsername("testuser").isEmpty()) {
				User user = new User("testuser", "plaintext"); // 仮。後でハッシュ化
				user.setName("テスト太郎");
				user.setRole("USER");
				userRepo.save(user);
			}
		};
	}
}
