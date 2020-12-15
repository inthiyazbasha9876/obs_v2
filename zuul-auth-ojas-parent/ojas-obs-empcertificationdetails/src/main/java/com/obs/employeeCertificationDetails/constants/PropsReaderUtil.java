package com.obs.employeeCertificationDetails.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@PropertySource("classpath:obs.queries.sql")
@Component
public class PropsReaderUtil {
@Autowired
Environment environment;

public String getValue(String key) {

return environment.getProperty(key);
}
}