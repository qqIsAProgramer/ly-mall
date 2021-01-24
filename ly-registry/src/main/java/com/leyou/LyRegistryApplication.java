package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: qyl
 * @Date: 2021/1/23 17:37
 */
@SpringBootApplication
@EnableEurekaServer
public class LyRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyRegistryApplication.class, args);
    }
}
