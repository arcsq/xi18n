package com.arcsq.poc.springcloud.xi18n;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigServer
@EnableFeignClients
public class Xi18nApplication {

    public static void main(final String[] args) {
        SpringApplication.run(Xi18nApplication.class, args);
    }

}
// http://localhost:8888/master/config-client-dev.json
// http://localhost:8888/config/master/config-client-dev.json
