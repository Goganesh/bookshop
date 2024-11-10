package com.goganesh.security;

import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import com.goganesh.otp.OtpConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import({ModelConfiguration.class, OtpConfiguration.class})
public class SecurityServiceConfiguration {

}
