package com.example.app.TestRestUserH2;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TestRestUserH2Application {

    public static void main(String[] args) {

        new SpringApplicationBuilder(TestRestUserH2Application.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);

        System.out.println("App running ...");
    }

}
