package org.ganmuren;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ganmuren
 */
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class}) //去除某自动配置的写法
@SpringBootApplication // = @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
