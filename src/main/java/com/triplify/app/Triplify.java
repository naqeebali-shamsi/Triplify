package com.triplify.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Triplify {

    public static void main(String[] args) {
        try {
            SpringApplication.run(Triplify.class, args);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
