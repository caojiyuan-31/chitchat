package org.ganmuren;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author ganmuren
 */
//@SpringBootApplication = @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication(exclude = {GsonAutoConfiguration.class}) //去除gson自动配置
public class Application //extends SpringBootServletInitializer
{

    //war包相关配置需继承SpringBootServletInitializer
    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
