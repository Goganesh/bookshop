package com.goganesh.bookshop.webui;

import com.goganesh.bookshop.common.CommonConfiguration;
import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import com.goganesh.bookshop.webapi.client.WebApiClientConfiguration;
import com.goganesh.security.SecurityServiceConfiguration;
import com.goganesh.security.configuration.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Locale;

@Configuration
@Import({ModelConfiguration.class, CommonConfiguration.class, SecurityConfiguration.class,
        SecurityServiceConfiguration.class, WebApiClientConfiguration.class})
@ComponentScan
public class WebUiConfiguration implements WebMvcConfigurer {

    private final String adminTemplatePrefix;
    private final String adminTemplateSuffix;
    private final String adminStaticResourcePrefix;
    private final String clientTemplatePrefix;
    private final String clientTemplateSuffix;
    private final String clientStaticResourcePrefix;

    public WebUiConfiguration(@Value("${com.goganesh.bookshop.webui-admin.template-prefix}") String adminTemplatePrefix,
                              @Value("${com.goganesh.bookshop.webui-admin.template-suffix}") String adminTemplateSuffix,
                              @Value("${com.goganesh.bookshop.webui-admin.static-resources-prefix}") String adminStaticResourcePrefix,
                              @Value("${com.goganesh.bookshop.webui-client.template-prefix}") String clientTemplatePrefix,
                              @Value("${com.goganesh.bookshop.webui-client.template-suffix}") String clientTemplateSuffix,
                              @Value("${com.goganesh.bookshop.webui-client.static-resources-prefix}") String clientStaticResourcePrefix) {
        this.adminTemplatePrefix = adminTemplatePrefix;
        this.adminTemplateSuffix = adminTemplateSuffix;
        this.adminStaticResourcePrefix = adminStaticResourcePrefix;
        this.clientTemplatePrefix = clientTemplatePrefix;
        this.clientTemplateSuffix = clientTemplateSuffix;
        this.clientStaticResourcePrefix = clientStaticResourcePrefix;
    }

    @Bean(name = "webUiAdminTemplateResolver")
    public SpringResourceTemplateResolver webUiAdminTemplateResolver() {
        var templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix(adminTemplatePrefix);
        templateResolver.setSuffix(adminTemplateSuffix);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        //templateResolver.setOrder(1);
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(true);

        return templateResolver;
    }

    @Bean(name = "webUiClientTemplateResolver")
    public SpringResourceTemplateResolver webUiClientTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix(clientTemplatePrefix);
        templateResolver.setSuffix(clientTemplateSuffix);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        //templateResolver.setOrder(0);
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(true);

        return templateResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations(adminStaticResourcePrefix, clientStaticResourcePrefix);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
