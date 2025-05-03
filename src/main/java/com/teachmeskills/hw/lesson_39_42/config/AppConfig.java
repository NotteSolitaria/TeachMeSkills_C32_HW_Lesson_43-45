package com.teachmeskills.hw.lesson_39_42.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan("com.teachmeskills.hw.lesson_39_42")
@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {
}
