package com.ojas.security.common;


import org.springframework.beans.factory.annotation.Value;


/**
 * Config JWT.
 * Only one property 'ojas.security.jwt.secret' is mandatory.
 *
 * 
 */

public class JwtAuthenticationConfig {

    @Value("${ojas.security.jwt.url:/obs/login}")
    private String url;

    @Value("${ojas.security.jwt.header:Authorization}")
    private String header;

    @Value("${ojas.security.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${ojas.security.jwt.expiration:#{24*60*60}}")
    private int expiration; // default 24 hours

    @Value("${ojas.security.jwt.secret}")
    private String secret;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getExpiration() {
		return expiration;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	
}
