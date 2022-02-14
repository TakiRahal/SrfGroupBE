package com.takirahal.srfgroup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SrfgroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrfgroupApplication.class, args);
    }

    @RequestMapping("/test")
    public String welcome(){
        return "Hello Boss";
    }
}
