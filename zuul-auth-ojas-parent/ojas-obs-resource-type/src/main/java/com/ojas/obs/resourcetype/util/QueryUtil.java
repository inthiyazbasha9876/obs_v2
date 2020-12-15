package com.ojas.obs.resourcetype.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@PropertySources({ @PropertySource("classpath:obs-queries.sql") })
@Component
public class QueryUtil {

	@Autowired
	Environment env;

	
	/**
	 * method returns query  based on the key from obs-queries properties file.
	 * @param key
	 * @return
	 */
	public String getQuery(String key) {

		return env.getProperty(key);
	}

}
