package com.ohdoking.useful.usefultoolproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({ 
	@PropertySource("classpath:properties/crawler.properties")
})
public class PropertiesWithJavaConfig {
    //...
}
