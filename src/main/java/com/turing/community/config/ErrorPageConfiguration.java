package com.turing.community.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Meng
 * @date 2019/8/20
 */
@Configuration
public class ErrorPageConfiguration {
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return (factory->{
            Set<ErrorPage> errorPageSet=new HashSet<>();
            errorPageSet.add(new ErrorPage(HttpStatus.NOT_FOUND,"/404.html"));
            errorPageSet.add(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/500.html"));
            factory.setErrorPages(errorPageSet);
        });
    }
}
