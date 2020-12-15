package com.ojas.obs.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@PropertySources({ @PropertySource("classpath:obs-queries.sql")})
@Component
public class PropsReaderUtil {
@Autowired
Environment environment;

public String getValue(String key) {

return environment.getProperty(key);
}
}