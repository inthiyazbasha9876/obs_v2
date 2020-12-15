package com.ojas.obs.tms.facade;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.Headers;
import feign.Response;

@FeignClient(name = "ojas-obs-employeeinfo", url = "${spring.feign.client.url.TestUrl}")
public interface FeignService {
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
	@Headers("Content-Type: application/json")
	public Response getEmployees(@RequestBody String str);
}
