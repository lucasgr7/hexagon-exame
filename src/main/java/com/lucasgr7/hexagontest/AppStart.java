package com.lucasgr7.hexagontest;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;


/**
 * Hello world!
 *
 */
@SpringBootApplication
@Import(SwaggerConfig.class)
public class AppStart extends SpringBootServletInitializer {

	private static Logger logger = LoggerFactory.getLogger(AppStart.class);
	
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = new SpringApplication(AppStart.class).run(args);
    	Environment env = ctx.getEnvironment();
    	logger.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://127.0.0.1:{}\n\t" +
                        "External: \thttp://{}:{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
	}
}
