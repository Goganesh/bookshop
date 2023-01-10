package com.goganesh.bookshop.webui.admin.configuration;

import com.goganesh.bookshop.webui.admin.controller.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
//@EnableWebMvc check spring solution for upgrade current solution
public class WebUiAdminConfiguration implements WebMvcConfigurer {

    private final String templatePrefix;
    private final String staticResourcePrefix;

    public WebUiAdminConfiguration(@Value("${com.goganesh.bookshop.webui-admin.template-prefix}") String templatePrefix,
                                   @Value("${com.goganesh.bookshop.webui-admin.static-resources-prefix}")String staticResourcesPrefix) {
        this.templatePrefix = templatePrefix;
        this.staticResourcePrefix = staticResourcesPrefix;
    }

    @Bean(name = "webUiAdminTemplateResolver")
    public SpringResourceTemplateResolver webUiAdminTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix(templatePrefix);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(1);
        templateResolver.setCheckExistence(true);

        return templateResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations(staticResourcePrefix);
    }

    @Bean
    public MainAdminPageController mainAdminPageController() {
        return new MainAdminPageController();
    }

    @Bean
    public AuthorAdminPageController authorAdminPageController() {
        return new AuthorAdminPageController();
    }

    @Bean
    public GenreAdminPageController genreAdminPageController() {
        return new GenreAdminPageController();
    }

    @Bean
    public BookAdminPageController bookAdminPageController() {
        return new BookAdminPageController();
    }

    @Bean
    public UserAdminPageController userAdminPageController() {
        return new UserAdminPageController();
    }
}
