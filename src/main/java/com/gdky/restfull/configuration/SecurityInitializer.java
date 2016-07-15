package com.gdky.restfull.configuration;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(1)
public class SecurityInitializer extends
		AbstractSecurityWebApplicationInitializer {
}