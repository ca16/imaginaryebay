package com.imaginaryebay.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Ben_Big on 6/30/16.
 */
@Configuration
public class MyWebMvcConfiguration{
        @Bean
        public WebMvcConfigurerAdapter forwardToIndex() {
            return new  WebMvcConfigurerAdapter() {
                @Override
                public void addViewControllers(ViewControllerRegistry registry) {
                    // forward requests to /admin and /user to their index.html
                    registry.addViewController("/").setViewName(
                            "forward:/webapp/index.html");
                }
            };
        }


}
