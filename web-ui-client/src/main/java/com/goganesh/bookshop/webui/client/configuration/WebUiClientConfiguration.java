package com.goganesh.bookshop.webui.client.configuration;

import com.goganesh.bookshop.common.configuration.CommonConfiguration;
import com.goganesh.bookshop.common.service.BookRatingService;
import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import com.goganesh.bookshop.model.service.*;
import com.goganesh.bookshop.webapi.client.configuration.WebApiClientConfiguration;
import com.goganesh.bookshop.webapi.client.service.BalanceTransactionRestService;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import com.goganesh.bookshop.webui.client.controller.*;
import com.goganesh.bookshop.webui.client.mapper.*;
import com.goganesh.bookshop.webui.client.service.TagSizeConfig;
import com.goganesh.bookshop.webui.client.service.TagSizeService;
import com.goganesh.bookshop.webui.client.service.impl.TagSizeConfigImpl;
import com.goganesh.bookshop.webui.client.service.impl.TagSizeServiceImpl;
import com.goganesh.security.configuration.SecurityConfiguration;
import com.goganesh.security.configuration.SecurityServiceConfiguration;
import com.goganesh.security.service.UserRegisterService;
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
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Locale;

@Configuration
//@EnableWebMvc check spring solution for upgrade current solution
@Import({ModelConfiguration.class, CommonConfiguration.class, SecurityConfiguration.class,
        SecurityServiceConfiguration.class, WebApiClientConfiguration.class})
@ComponentScan(basePackages = {"com.goganesh.bookshop.webui.client.configuration"})
public class WebUiClientConfiguration implements WebMvcConfigurer {

    private final String templatePrefix;
    private final String templateSuffix;
    private final String staticResourcePrefix;

    public WebUiClientConfiguration(@Value("${com.goganesh.bookshop.webui-client.template-prefix}") String templatePrefix,
                                    @Value("${com.goganesh.bookshop.webui-client.template-suffix}") String templateSuffix,
                                    @Value("${com.goganesh.bookshop.webui-client.static-resources-prefix}") String staticResourcesPrefix) {
        this.templatePrefix = templatePrefix;
        this.templateSuffix = templateSuffix;
        this.staticResourcePrefix = staticResourcesPrefix;
    }

    @Bean(name = "webUiClientTemplateResolver")
    public SpringResourceTemplateResolver webUiClientTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix(templatePrefix);
        templateResolver.setSuffix(templateSuffix);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(0);
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


    //controllers
    @Bean
    public AboutPageController aboutPageController(UserRegisterService userRegisterService, UserMapper userMapper) {
        return new AboutPageController(userRegisterService, userMapper);
    }

    @Bean
    public AuthorPageController authorPageController(AuthorReadRepository authorReadRepository,
                                                     BookRestService bookRestService,
                                                     BookPageMapper bookMapper,
                                                     UserRegisterService userRegisterService,
                                                     AuthorMapper authorMapper,
                                                     UserMapper userMapper) {
        return AuthorPageController.builder()
                .authorMapper(authorMapper)
                .authorReadRepository(authorReadRepository)
                .bookMapper(bookMapper)
                .bookRestService(bookRestService)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public AuthorsPageController authorsPageController(AuthorReadRepository authorReadRepository,
                                                       UserRegisterService userRegisterService,
                                                       AuthorMapper authorMapper,
                                                       UserMapper userMapper) {
        return AuthorsPageController.builder()
                .authorMapper(authorMapper)
                .authorReadRepository(authorReadRepository)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public BookPageController bookPageController(BookReadRepository bookReadRepository,
                                                 BookFileReadRepository bookFileReadRepository,
                                                 BookPageMapper bookMapper,
                                                 UserRegisterService userRegisterService,
                                                 BookReviewReadRepository bookReviewReadRepository,
                                                 Book2UserWriteRepository book2UserWriteRepository,
                                                 Book2UserTypeReaRepository book2UserTypeReaRepository,
                                                 BookFileMapper bookFileMapper,
                                                 BookReviewMapper bookReviewMapper,
                                                 UserMapper userMapper) {
        return BookPageController.builder()
                .book2UserTypeReaRepository(book2UserTypeReaRepository)
                .book2UserWriteRepository(book2UserWriteRepository)
                .bookFileMapper(bookFileMapper)
                .bookFileReadRepository(bookFileReadRepository)
                .bookMapper(bookMapper)
                .bookReadRepository(bookReadRepository)
                .bookReviewMapper(bookReviewMapper)
                .bookReviewReadRepository(bookReviewReadRepository)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public BooksAuthorPageController booksAuthorPageController(BookRestService bookRestService,
                                                               AuthorReadRepository authorReadRepository,
                                                               BookPageMapper bookMapper,
                                                               UserRegisterService userRegisterService,
                                                               AuthorMapper authorMapper,
                                                               UserMapper userMapper) {
        return BooksAuthorPageController.builder()
                .authorMapper(authorMapper)
                .authorReadRepository(authorReadRepository)
                .bookMapper(bookMapper)
                .bookRestService(bookRestService)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public BooksPopularPageController booksPopularPageController(BookRestService bookRestService,
                                                                 BookPageMapper bookMapper,
                                                                 UserRegisterService userRegisterService,
                                                                 UserMapper userMapper) {
        return BooksPopularPageController.builder()
                .bookMapper(bookMapper)
                .bookRestService(bookRestService)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public BooksRecentPageController booksRecentPageController(BookRestService bookRestService,
                                                               BookPageMapper bookMapper,
                                                               UserRegisterService userRegisterService,
                                                               UserMapper userMapper) {
        return BooksRecentPageController.builder()
                .bookMapper(bookMapper)
                .bookRestService(bookRestService)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public BooksViewedPageController booksViewedPageController(BookRestService bookRestService,
                                                               BookPageMapper bookMapper,
                                                               UserRegisterService userRegisterService,
                                                               UserMapper userMapper) {
        return BooksViewedPageController.builder()
                .bookMapper(bookMapper)
                .bookRestService(bookRestService)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public CartPageController cartPageController(Book2UserTypeReaRepository book2UserTypeReaRepository,
                                                 UserRegisterService userRegisterService,
                                                 Book2UserReadRepository book2UserReadRepository,
                                                 BookPageMapper bookMapper,
                                                 UserMapper userMapper) {
        return CartPageController.builder()
                .book2UserReadRepository(book2UserReadRepository)
                .book2UserTypeReaRepository(book2UserTypeReaRepository)
                .bookMapper(bookMapper)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public ContactsPageController contactsPageController(UserRegisterService userRegisterService, UserMapper userMapper) {
        return new ContactsPageController(userRegisterService, userMapper);
    }

    @Bean
    public DocumentsPageController documentsPageController(UserRegisterService userRegisterService, UserMapper userMapper) {
        return new DocumentsPageController(userRegisterService, userMapper);
    }

    @Bean
    public FaqPageController faqPageController(UserRegisterService userRegisterService, UserMapper userMapper) {
        return new FaqPageController(userRegisterService, userMapper);
    }

    @Bean
    public GenrePageController genrePageController(GenreReadRepository genreReadRepository,
                                                   BookRestService bookRestService,
                                                   BookPageMapper bookMapper,
                                                   UserRegisterService userRegisterService,
                                                   GenreMapper genreMapper,
                                                   UserMapper userMapper) {
        return GenrePageController.builder()
                .bookMapper(bookMapper)
                .genreMapper(genreMapper)
                .genreReadRepository(genreReadRepository)
                .bookRestService(bookRestService)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public GenresPageController genresPageController(GenreReadRepository genreReadRepository,
                                                     UserRegisterService userRegisterService,
                                                     GenreMapper genreMapper,
                                                     UserMapper userMapper) {
        return GenresPageController.builder()
                .genreMapper(genreMapper)
                .genreReadRepository(genreReadRepository)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public IndexPageController indexPageController(BookRestService bookRestService,
                                                   TagReadRepository tagReadRepository,
                                                   BookPageMapper bookMapper,
                                                   UserRegisterService userRegisterService,
                                                   TagMapper tagMapper,
                                                   UserMapper userMapper) {
        return IndexPageController.builder()
                .bookMapper(bookMapper)
                .bookRestService(bookRestService)
                .tagMapper(tagMapper)
                .tagReadRepository(tagReadRepository)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public MyArchivePageController myArchivePageController(UserRegisterService userRegisterService,
                                                           BookRestService bookRestService,
                                                           BookPageMapper bookMapper,
                                                           UserMapper userMapper) {
        return MyArchivePageController.builder()
                .bookMapper(bookMapper)
                .bookRestService(bookRestService)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public MyPageController myPageController(UserRegisterService userRegisterService,
                                             BookRestService bookRestService,
                                             BookPageMapper bookMapper,
                                             UserMapper userMapper) {
        return MyPageController.builder()
                .bookMapper(bookMapper)
                .bookRestService(bookRestService)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public PostponedPageController postponedPageController(UserRegisterService userRegisterService,
                                                           Book2UserReadRepository book2UserReadRepository,
                                                           Book2UserTypeReaRepository book2UserTypeReaRepository,
                                                           BookPageMapper bookMapper,
                                                           UserMapper userMapper) {
        return PostponedPageController.builder()
                .bookMapper(bookMapper)
                .book2UserReadRepository(book2UserReadRepository)
                .userMapper(userMapper)
                .book2UserTypeReaRepository(book2UserTypeReaRepository)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public ProfilePageController profilePageController(UserRegisterService userRegisterService,
                                                       BalanceTransactionRestService balanceTransactionRestService,
                                                       BalanceTransactionMapper balanceTransactionMapper,
                                                       UserMapper userMapper) {
        return ProfilePageController.builder()
                .balanceTransactionRestService(balanceTransactionRestService)
                .balanceTransactionMapper(balanceTransactionMapper)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public SearchPageController searchPageController(BookRestService bookRestService,
                                                     BookPageMapper bookMapper,
                                                     UserRegisterService userRegisterService,
                                                     UserMapper userMapper) {
        return SearchPageController.builder()
                .bookMapper(bookMapper)
                .bookRestService(bookRestService)
                .userMapper(userMapper)
                .userRegisterService(userRegisterService)
                .build();
    }

    @Bean
    public SigninPageController signinPageController(UserRegisterService userRegisterService, UserMapper userMapper) {
        return new SigninPageController(userRegisterService, userMapper);
    }

    @Bean
    public SignupPageController signupPageController(UserRegisterService userRegisterService, UserMapper userMapper) {
        return new SignupPageController(userRegisterService, userMapper);
    }

    @Bean
    public TagPageController tagPageController(TagReadRepository tagReadRepository,
                                               BookRestService bookRestService,
                                               BookPageMapper bookMapper,
                                               UserRegisterService userRegisterService,
                                               TagMapper tagMapper,
                                               UserMapper userMapper) {
        return TagPageController.builder()
                .tagMapper(tagMapper)
                .userRegisterService(userRegisterService)
                .tagReadRepository(tagReadRepository)
                .bookMapper(bookMapper)
                .bookRestService(bookRestService)
                .userMapper(userMapper)
                .build();
    }

    //services
    @Bean
    public TagSizeConfig tagSizeConfig() {
        return new TagSizeConfigImpl();
    }

    @Bean
    public TagSizeService tagSizeService(BookReadRepository bookReadRepository, TagSizeConfig tagSizeConfig) {
        return new TagSizeServiceImpl(bookReadRepository, tagSizeConfig);
    }

    //mappers
    @Bean
    public BookPageMapper bookPageMapper(BookRatingService bookRatingService) {
        BookPageMapper bookMapper = new BookPageMapperImpl();
        bookMapper.setBookRatingService(bookRatingService);

        return bookMapper;
    }

    @Bean
    public BookFileMapper bookFileMapper() {
        return new BookFileMapperImpl();
    }

    @Bean
    public BalanceTransactionMapper balanceTransactionMapper() {
        return new BalanceTransactionMapperImpl();
    }

    @Bean
    public BookReviewMapper bookReviewMapper() {
        return new BookReviewMapperImpl();
    }

    @Bean
    public GenreMapper genreMapper(BookReadRepository bookReadRepository) {
        GenreMapper genreMapper = new GenreMapperImpl();
        genreMapper.setBookReadRepository(bookReadRepository);

        return genreMapper;
    }

    @Bean
    public TagMapper tagMapper(BookReadRepository bookReadRepository, TagSizeService tagSizeService) {
        TagMapper tagMapper = new TagMapperImpl();
        tagMapper.setTagSizeService(tagSizeService);
        tagMapper.setBookReadRepository(bookReadRepository);

        return tagMapper;
    }

    @Bean
    public AuthorMapper authorMapper(BookReadRepository bookReadRepository) {
        AuthorMapper authorMapper = new AuthorMapperImpl();
        authorMapper.setBookReadRepository(bookReadRepository);

        return authorMapper;
    }

    @Bean
    public UserMapper userMapper(Book2UserReadRepository book2UserReadRepository) {
        UserMapper userMapper = new UserMapperImpl();
        userMapper.setBook2UserReadRepository(book2UserReadRepository);

        return userMapper;
    }
}
