package ru.gb.auth.configs;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Slf4j
@Configuration
@Profile("integration-test")
public class IntegrationTestConfig {

    @Value("${postgres.image}")
    private String postgresImage;

    @Value("${postgres.database.name}")
    private String postgresDatabaseName;

    @Value("${postgres.init.script.path}")
    private String postgresInitScriptPath;

    @Value("${postgres.username}")
    private String postgresUsername;

    @Value("${postgres.password}")
    private String postgresPassword;

    @Value("${liquibase.default.schema}")
    private String liquibaseDefaultSchema;

    @Value("${liquibase.master.changelog.path}")
    private String liquibaseMasterChangelogPath;

    @Bean
    @NonNull
    public PostgreSQLContainer<?> postgreSQLContainer(final Network network) {
        PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(postgresImage)
                .withDatabaseName(postgresDatabaseName)
                .withInitScript(postgresInitScriptPath)
                .withUsername(postgresUsername)
                .withPassword(postgresPassword)
                .withNetwork(network);

        postgreSQLContainer.start();
        log.info(postgreSQLContainer.getJdbcUrl());

        return postgreSQLContainer;
    }

    @Bean
    @NonNull
    public DataSource dataSource(
            final PostgreSQLContainer<?> postgreSQLContainer,
            final DataSourceProperties dataSourceProperties
    ) {
        dataSourceProperties.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSourceProperties.setUsername(postgreSQLContainer.getUsername());
        dataSourceProperties.setPassword(postgreSQLContainer.getPassword());

        return dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @NonNull
    public Network network() {
        return Network.newNetwork();
    }

    @Bean
    @NonNull
    public MockMvc mockMvc(final WebApplicationContext webApplicationContext) {
        return MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @Bean
    @NonNull
    public SpringLiquibase springLiquibase(@NonNull final DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(liquibaseDefaultSchema);
        liquibase.setChangeLog(liquibaseMasterChangelogPath);

        return liquibase;
    }

    @Bean
    @NonNull
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @NonNull
    public DataSourceTransactionManager transactionManager(@NonNull final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
