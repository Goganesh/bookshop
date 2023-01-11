package com.goganesh.bookshop.model.configuration;

import com.goganesh.bookshop.model.repository.*;
import com.goganesh.bookshop.model.service.*;
import com.goganesh.bookshop.model.service.impl.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.goganesh.bookshop.model.repository")
public class ModelConfiguration {

    @Primary
    @Bean(name = "dataSourceProp")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource(@Qualifier("dataSourceProp") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("dataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.goganesh.bookshop.model.domain")
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public AuthorReadRepository authorReadRepository(JpaAuthorRepository authorRepository) {
        return new JpaAuthorReadRepository(authorRepository);
    }

    @Bean
    public InvalidTokenReadRepository invalidTokenReadRepository(JpaInvalidTokenRepository jpaInvalidTokenRepository) {
        return new JpaInvalidTokenReadRepository(jpaInvalidTokenRepository);
    }

    @Bean
    public InvalidTokenWriteRepository invalidTokenWriteRepository(JpaInvalidTokenRepository jpaInvalidTokenRepository) {
        return new JpaInvalidTokenWriteRepository(jpaInvalidTokenRepository);
    }

    @Bean
    public UserReadRepository userReadRepository(JpaUserRepository jpaUserRepository) {
        return new JpaUserReadRepository(jpaUserRepository);
    }

    @Bean
    public UserWriteRepository userWriteRepository(JpaUserRepository jpaUserRepository) {
        return new JpaUserWriteRepository(jpaUserRepository);
    }

    @Bean
    public Book2UserReadRepository book2UserReadRepository(JpaBook2UserRepository jpaBook2UserRepository) {
        return new JpaBook2UserReadRepository(jpaBook2UserRepository);
    }

    @Bean
    public Book2UserWriteRepository book2UserWriteRepository(JpaBook2UserRepository jpaBook2UserRepository) {
        return new JpaBook2UserWriteRepository(jpaBook2UserRepository);
    }

    @Bean
    public UserContactReadRepository userContactReadRepository(JpaUserContactRepository jpaUserContactRepository) {
        return new JpaUserContactReadRepository(jpaUserContactRepository);
    }

    @Bean
    public UserContactWriteRepository userContactWriteRepository(JpaUserContactRepository jpaUserContactRepository) {
        return new JpaUserContactWriteRepository(jpaUserContactRepository);
    }

    @Bean
    public BookReadRepository bookReadRepository(JpaBookRepository jpaBookRepository) {
        return new JpaBookReadRepository(jpaBookRepository);
    }

    @Bean
    public BookWriteRepository bookWriteRepository(JpaBookRepository jpaBookRepository) {
        return new JpaBookWriteRepository(jpaBookRepository);
    }

    @Bean
    public BookRatingReadRepository bookRatingReadRepository(JpaBookRatingRepository jpaBookRatingRepository) {
        return new JpaBookRatingReadRepository(jpaBookRatingRepository);
    }

    @Bean
    public BookRatingWriteRepository bookRatingWriteRepository(JpaBookRatingRepository jpaBookRatingRepository) {
        return new JpaBookRatingWriteRepository(jpaBookRatingRepository);
    }

    @Bean
    public GenreReadRepository genreReadRepository(JpaGenreRepository jpaGenreRepository) {
        return new JpaGenreReadRepository(jpaGenreRepository);
    }

    @Bean
    public GenreWriteRepository genreWriteRepository(JpaGenreRepository jpaGenreRepository) {
        return new JpaGenreWriteRepository(jpaGenreRepository);
    }

    @Bean
    public TagReadRepository tagReadRepository(JpaTagRepository jpaTagRepository) {
        return new JpaTagReadRepository(jpaTagRepository);
    }

    @Bean
    public BookReviewWriteRepository bookReviewWriteRepository(JpaBookReviewRepository jpaBookReviewRepository) {
        return new JpaBookReviewWriteRepository(jpaBookReviewRepository);
    }

    @Bean
    public BookReviewReadRepository bookReviewReadRepository(JpaBookReviewRepository jpaBookReviewRepository) {
        return new JpaBookReviewReadRepository(jpaBookReviewRepository);
    }

    @Bean
    public BookReviewLikeReadRepository bookReviewLikeReadRepository(JpaBookReviewLikeRepository jpaBookReviewLikeRepository) {
        return new JpaBookReviewLikeReadRepository(jpaBookReviewLikeRepository);
    }

    @Bean
    public BookReviewLikeWriteRepository bookReviewLikeWriteRepository(JpaBookReviewLikeRepository jpaBookReviewLikeRepository) {
        return new JpaBookReviewLikeWriteRepository(jpaBookReviewLikeRepository);
    }

    @Bean
    public BalanceTransactionWriteRepository balanceTransactionWriteRepository(JpaBalanceTransactionRepository jpaBalanceTransactionRepository) {
        return new JpaBalanceTransactionWriteRepository(jpaBalanceTransactionRepository);
    }

    @Bean
    public BalanceTransactionReadRepository balanceTransactionReadRepository(JpaBalanceTransactionRepository jpaBalanceTransactionRepository) {
        return new JpaBalanceTransactionReadRepository(jpaBalanceTransactionRepository);
    }

    @Bean
    public LoginAttemptReadRepository loginAttemptReadRepository(JpaLoginAttemptRepository jpaLoginAttemptRepository) {
        return new JpaLoginAttemptReadRepository(jpaLoginAttemptRepository);
    }

    @Bean
    public LoginAttemptWriteRepository loginAttemptWriteRepository(JpaLoginAttemptRepository jpaLoginAttemptRepository) {
        return new JpaLoginAttemptWriteRepository(jpaLoginAttemptRepository);
    }

    @Bean
    public Book2UserTypeReaRepository book2UserTypeReaRepository(JpaBook2UserTypeRepository jpaBook2UserTypeRepository) {
        return new JpaBook2UserTypeReaRepository(jpaBook2UserTypeRepository);
    }

    @Bean
    public BookFileReadRepository bookFileReadRepository(JpaBookFileRepository jpaBookFileRepository) {
        return new JpaBookFileReadRepository(jpaBookFileRepository);
    }

    @Bean
    public ActivityWriteRepository activityWriteRepository(JpaActivityRepository jpaActivityRepository) {
        return new JpaActivityWriteRepository(jpaActivityRepository);
    }

    @Bean
    public ActivityReadRepository activityReadRepository(JpaActivityRepository jpaActivityRepository) {
        return new JpaActivityReadRepository(jpaActivityRepository);
    }

    @Bean
    public AuthorWriteRepository authorWriteRepository(JpaAuthorRepository jpaAuthorRepository) {
        return new JpaAuthorWriteRepository(jpaAuthorRepository);
    }
}
