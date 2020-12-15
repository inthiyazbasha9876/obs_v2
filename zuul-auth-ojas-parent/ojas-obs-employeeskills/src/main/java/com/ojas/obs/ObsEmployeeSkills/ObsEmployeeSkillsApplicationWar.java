package com.ojas.obs.ObsEmployeeSkills;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ObsEmployeeSkillsApplicationWar extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ObsEmployeeSkillsApplication.class);
	}
}
