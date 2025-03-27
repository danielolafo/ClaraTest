package com.clara.test.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;



@PropertySource({ 
	  "classpath:application.properties"
	})
public class Constants {

	@Value("${external.api.discog.token}")
	public String discogToken;
	
}
