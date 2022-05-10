package com.toolsofsoftwareprojects.cookaholic_backend;

import com.toolsofsoftwareprojects.cookaholic_backend.repo.CookaholicUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CookaholicBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CookaholicBackendApplication.class, args);
    }

    @Autowired
    private CookaholicUserRepo userRepository;

    @Autowired
    private CookaholicUserRepo passwordEncoder;

}
