package com.comment.insight.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

    @PostConstruct
    public void checkEnv() {
        System.out.println("SERVER_PORT = " + System.getenv("SERVER_PORT"));
        System.out.println("CONFIG_REPO_URL = " + System.getenv("CONFIG_REPO_URL"));
    }

}
