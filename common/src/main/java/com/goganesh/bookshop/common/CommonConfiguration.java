package com.goganesh.bookshop.common;

import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
@Import(ModelConfiguration.class)
public class CommonConfiguration {


}
