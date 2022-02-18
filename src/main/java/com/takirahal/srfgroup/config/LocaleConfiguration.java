package com.takirahal.srfgroup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;


public class LocaleConfiguration{}

//@Configuration
//public class LocaleConfiguration implements WebMvcConfigurer {
//
//    @Bean
//    public LocaleResolver localeResolver() {
//        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
//        acceptHeaderLocaleResolver.setDefaultLocale(new Locale("en"));
//        return acceptHeaderLocaleResolver;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//        localeChangeInterceptor.setParamName("language");
//        registry.addInterceptor(localeChangeInterceptor);
//    }
//}