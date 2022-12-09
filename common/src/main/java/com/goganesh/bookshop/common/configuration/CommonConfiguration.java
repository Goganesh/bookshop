package com.goganesh.bookshop.common.configuration;

import com.goganesh.bookshop.common.aop.aspect.ActivityCounterMetricAspect;
import com.goganesh.bookshop.common.aop.aspect.LogExecutionTimeAspect;
import com.goganesh.bookshop.common.aop.aspect.LoggingAspect;
import com.goganesh.bookshop.common.service.*;
import com.goganesh.bookshop.common.service.impl.*;
import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import com.goganesh.bookshop.model.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ModelConfiguration.class)
public class CommonConfiguration {

    @Bean
    public BookActionKept bookActionKept(Book2UserReadRepository book2UserReadRepository,
                                         Book2UserWriteRepository book2UserWriteRepository,
                                         Book2UserTypeReaRepository book2UserTypeReaRepository) {
        return new BookActionKept(book2UserReadRepository, book2UserWriteRepository, book2UserTypeReaRepository);
    }

    @Bean
    public BookActionUnlink bookActionUnlink(Book2UserReadRepository book2UserReadRepository,
                                         Book2UserWriteRepository book2UserWriteRepository,
                                         Book2UserTypeReaRepository book2UserTypeReaRepository) {
        return new BookActionUnlink(book2UserReadRepository, book2UserWriteRepository, book2UserTypeReaRepository);
    }

    @Bean
    public BookActionCart bookActionCart(Book2UserReadRepository book2UserReadRepository,
                                             Book2UserWriteRepository book2UserWriteRepository,
                                             Book2UserTypeReaRepository book2UserTypeReaRepository) {
        return new BookActionCart(book2UserReadRepository, book2UserWriteRepository, book2UserTypeReaRepository);
    }

    @Bean
    public BookActionService bookActionService(BookActionKept bookActionKept,
                                               BookActionUnlink bookActionUnlink,
                                               BookActionCart bookActionCart) {
        return new BookActionServiceImpl(bookActionKept, bookActionUnlink, bookActionCart);
    }

    @Bean
    public BookRatingService bookRatingService(BookRatingReadRepository bookRatingReadRepository, BookRatingWriteRepository bookRatingWriteRepository) {
        return new BookRatingServiceImpl(bookRatingReadRepository, bookRatingWriteRepository);
    }

    @Bean
    public ActivityCounterService activityCounterService(ActivityReadRepository activityReadRepository, ActivityWriteRepository activityWriteRepository) {
        return new ActivityCounterServiceImpl(activityWriteRepository, activityReadRepository);
    }

    @Bean
    public BookPopularityServiceConfig bookPopularityServiceConfig(@Value("${com.goganesh.bookshop.popularity-service.rate.paid}") Double paidRateConfig,
                                                                   @Value("${com.goganesh.bookshop.popularity-service.rate.cart}") Double cartRateConfig,
                                                                   @Value("${com.goganesh.bookshop.popularity-service.rate.kept}") Double keptRateConfig,
                                                                   @Value("${com.goganesh.bookshop.popularity-service.rate.viewed}") Double viewedRateConfig,
                                                                   @Value("${com.goganesh.bookshop.popularity-service.rate.default}") Double defaultRateConfig,
                                                                   @Value("${com.goganesh.bookshop.popularity-service.viewed-time-limit-minutes}") Integer viewedTimeLimitMinutesConfig) {
        return new BookPopularityServiceConfigImpl(paidRateConfig, cartRateConfig, keptRateConfig, viewedRateConfig, defaultRateConfig, viewedTimeLimitMinutesConfig);
    }

    @Bean
    public BookPopularityService bookPopularityService(BookWriteRepository bookWriteRepository,
                                                       Book2UserReadRepository book2UserReadRepository,
                                                       BookPopularityServiceConfig popularityServiceConfig) {
        return new BookPopularityServiceImpl(bookWriteRepository, book2UserReadRepository, popularityServiceConfig);
    }

    @Bean
    public ActivityCounterMetricAspect activityCounterMetricAspect(ActivityCounterService activityCounterService) {
        return new ActivityCounterMetricAspect(activityCounterService);
    }

    @Bean
    public LogExecutionTimeAspect logExecutionTimeAspect() {
        return new LogExecutionTimeAspect();
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean
    public FileStorageService fileStorageService() {
        return new FileStorageServiceImpl();
    }

    @Bean
    public ImageService imageService(FileStorageService fileStorageService,
                                     @Value("${com.goganesh.bookshop.filestorage-service.book.image.directory}") String bookImageDirectory) {
        return new ImageServiceImpl(fileStorageService, bookImageDirectory);
    }
}
