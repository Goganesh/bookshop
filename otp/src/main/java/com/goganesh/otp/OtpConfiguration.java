package com.goganesh.otp;

import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ModelConfiguration.class})
@ComponentScan
public class OtpConfiguration {


}
