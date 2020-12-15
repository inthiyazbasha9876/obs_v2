package com.ojas.obs.passport.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

//connect with queries.sql file
@PropertySources({ @PropertySource("classpath:queries.sql")})
@Component
public class PropsReaderUtil {
	
@Autowired
Environment environment;

public String getValue(String key) {

return environment.getProperty(key);
}
}
