package com.bhaviCodes.wikimediaConsumer;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootConsumerApplication implements CommandLineRunner {
//consumer starter
    public static void main(String[] args) {
        SpringApplication.run(SpringBootConsumerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

    }
}
