package com.goganesh.bookshop.webapi.client;

import com.goganesh.bookshop.common.CommonConfiguration;
import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import com.goganesh.security.configuration.SecurityConfiguration;
import com.goganesh.security.SecurityServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import({ModelConfiguration.class, CommonConfiguration.class, SecurityConfiguration.class, SecurityServiceConfiguration.class})
public class WebApiClientConfiguration {

}
