package org.chance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * -Dserver.port=8080 -Dh2.tcp.port=9092 spring.profiles.active=dev
 * -Dserver.port=8081 spring.profiles.active=dev2
 *
 * dev2 依赖dev的数据库
 *
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
