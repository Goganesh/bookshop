package com.goganesh.bookshop.webapi.client.configuration;

import com.goganesh.bookshop.common.configuration.CommonConfiguration;
import com.goganesh.bookshop.common.service.BookActionService;
import com.goganesh.bookshop.common.service.BookRatingService;
import com.goganesh.bookshop.common.service.ImageService;
import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import com.goganesh.bookshop.model.service.*;
import com.goganesh.bookshop.webapi.client.controller.*;
import com.goganesh.bookshop.webapi.client.converter.ChangeBookStatusDtoDeserializer;
import com.goganesh.bookshop.webapi.client.mapper.*;
import com.goganesh.bookshop.webapi.client.service.AuthorRestService;
import com.goganesh.bookshop.webapi.client.service.GenreRestService;
import com.goganesh.bookshop.webapi.client.service.impl.AuthorRestServiceImpl;
import com.goganesh.bookshop.webapi.client.service.impl.BalanceTransactionRestServiceImpl;
import com.goganesh.bookshop.webapi.client.service.impl.BookRestServiceImpl;
import com.goganesh.bookshop.webapi.client.service.BalanceTransactionRestService;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import com.goganesh.bookshop.webapi.client.service.impl.GenreRestServiceImpl;
import com.goganesh.bookshop.webapi.client.validation.NoPathTraversalValidator;
import com.goganesh.security.configuration.SecurityConfiguration;
import com.goganesh.security.configuration.SecurityServiceConfiguration;
import com.goganesh.security.service.UserRegisterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ModelConfiguration.class, CommonConfiguration.class, SecurityConfiguration.class, SecurityServiceConfiguration.class})
public class WebApiClientConfiguration {

    @Bean
    public NoPathTraversalValidator noPathTraversalValidator() {
        return new NoPathTraversalValidator();
    }

    @Bean
    public BookRestService bookRestService(BookReadRepository bookReadRepository) {
        return new BookRestServiceImpl(bookReadRepository);
    }

    @Bean
    public GenreRestService genreRestService(GenreReadRepository genreReadRepository,
                                             GenreWriteRepository genreWriteRepository) {
        return new GenreRestServiceImpl(genreReadRepository, genreWriteRepository);
    }

    @Bean
    public AuthorRestService authorRestService(AuthorReadRepository authorReadRepository,
                                               AuthorWriteRepository authorWriteRepository) {
        return new AuthorRestServiceImpl(authorReadRepository, authorWriteRepository);
    }

    @Bean
    public BalanceTransactionRestService balanceTransactionRestService(BalanceTransactionReadRepository balanceTransactionReadRepository) {
        return new BalanceTransactionRestServiceImpl(balanceTransactionReadRepository);
    }

    @Bean
    public BookMapper bookMapper(BookRatingService bookRatingService) {
        BookMapper bookMapper = new BookMapperImpl();
        bookMapper.setBookRatingService(bookRatingService);

        return bookMapper;
    }

    @Bean
    public GenreApiMapper genreApiMapper(GenreReadRepository genreReadRepository) {
        GenreApiMapper genreApiMapper = new GenreApiMapperImpl();

        return genreApiMapper;
    }

    @Bean
    public BalanceTransactionMapper transactionMapper() {
        return new BalanceTransactionMapperImpl();
    }

    @Bean
    public AuthorApiMapper authorApiMapper() {
        return new AuthorApiMapperImpl();
    }

    @Bean
    public BookApiMapper bookApiMapper() {
        return new BookApiMapperImpl();
    }

    @Bean
    public DataApiController dataApiController(BookRestService bookRestService,
                                               GenreReadRepository genreReadRepository,
                                               AuthorReadRepository authorReadRepository,
                                               TagReadRepository tagReadRepository,
                                               UserRegisterService userRegisterService,
                                               BookMapper bookMapper) {
        return new DataApiController(bookRestService, genreReadRepository, authorReadRepository, tagReadRepository, userRegisterService, bookMapper);
    }

    @Bean
    public GenreController genreController(GenreRestService genreRestService,
                                           GenreApiMapper genreApiMapper) {
        return new GenreController(genreRestService, genreApiMapper);
    }

    @Bean
    public AuthorController authorController(AuthorRestService authorRestService,
                                             AuthorApiMapper authorApiMapper) {
        return new AuthorController(authorRestService, authorApiMapper);
    }

    @Bean
    public PaymentController paymentController(UserRegisterService userRegisterService,
                                               BalanceTransactionRestService balanceTransactionRestService,
                                               BalanceTransactionWriteRepository balanceTransactionWriteRepository,
                                               BalanceTransactionMapper transactionMapper) {
        return new PaymentController(userRegisterService, balanceTransactionRestService, balanceTransactionWriteRepository, transactionMapper);
    }

    @Bean
    public RateController rateController(BookRatingWriteRepository bookRatingWriteRepository,
                                         BookRatingReadRepository bookRatingReadRepository,
                                         BookReadRepository bookReadRepository,
                                         UserRegisterService userRegisterService) {
        return new RateController(bookRatingWriteRepository, bookRatingReadRepository, bookReadRepository, userRegisterService);
    }

    @Bean
    public ReviewController reviewController(BookReviewWriteRepository bookReviewWriteRepository,
                                             BookReviewReadRepository bookReviewReadRepository,
                                             BookReviewLikeReadRepository bookReviewLikeReadRepository,
                                             BookReviewLikeWriteRepository bookReviewLikeWriteRepository,
                                             BookReadRepository bookReadRepository,
                                             UserRegisterService userRegisterService) {
        return new ReviewController(bookReviewWriteRepository, bookReviewReadRepository, bookReviewLikeReadRepository,
                bookReviewLikeWriteRepository, bookReadRepository, userRegisterService);
    }

    @Bean
    public ShopApiController shopApiController(UserRegisterService userRegisterService,
                                               BookActionService bookActionService,
                                               BookReadRepository bookReadRepository) {
        return new ShopApiController(userRegisterService, bookActionService, bookReadRepository);
    }

    @Bean
    public AdminController adminController(BookWriteRepository bookWriteRepository,
                                           BookReadRepository bookReadRepository,
                                           AuthorReadRepository authorReadRepository,
                                           AuthorWriteRepository authorWriteRepository,
                                           AuthorApiMapper authorApiMapper,
                                           BookApiMapper bookApiMapper,
                                           ImageService imageService) {
        return new AdminController(
                bookWriteRepository,
                bookReadRepository,
                authorReadRepository,
                authorWriteRepository,
                authorApiMapper,
                bookApiMapper,
                imageService);
    }

    @Bean
    public ChangeBookStatusDtoDeserializer changeBookStatusDtoDeserializer() {
        return new ChangeBookStatusDtoDeserializer();
    }


}
