package com.goganeah.bookshop.app.configuration;

import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import com.goganesh.bookshop.webapi.client.configuration.WebApiClientConfiguration;
import com.goganesh.bookshop.webui.admin.configuration.WebUiAdminConfiguration;
import com.goganesh.bookshop.webui.client.configuration.WebUiClientConfiguration;
import com.goganesh.migration.configuration.MigrationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ModelConfiguration.class, WebApiClientConfiguration.class,
        MigrationConfiguration.class, WebUiClientConfiguration.class,
        WebUiAdminConfiguration.class})
public class ApplicationConfiguration {
}
