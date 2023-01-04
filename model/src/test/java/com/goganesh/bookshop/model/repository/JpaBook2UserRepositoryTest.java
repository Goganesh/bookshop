package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.domain.Book2UserType;
import com.goganesh.bookshop.model.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {ModelConfiguration.class})
@DataJpaTest
@Disabled
class JpaBook2UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JpaBook2UserRepository jpaBook2UserRepository;

    private User expectedUser;
    private Book expectedBook1;
    private Book expectedBook2;
    private Book2UserType expectedType;

    @BeforeEach
    void setUp() {
        expectedUser = entityManager.persistFlushFind(User.builder()
                .name("David")
                .build());

        expectedBook1 = entityManager.persistFlushFind(Book.builder()
                .title("War and Peace")
                .build());

        expectedBook2 = entityManager.persistFlushFind(Book.builder()
                .title("Anna Karenina")
                .build());


        expectedType = entityManager.persistFlushFind(Book2UserType.builder()
                .code("hold")
                .build());

        entityManager.persistAndFlush(Book2User.builder()
                .book(expectedBook1)
                .user(expectedUser)
                .book2UserType(expectedType)
                .build());

        entityManager.persistAndFlush(Book2User.builder()
                .book(expectedBook2)
                .user(expectedUser)
                .book2UserType(expectedType)
                .build());
    }

    @Test
    void findByBook() {
        assertEquals(1, jpaBook2UserRepository.findByBook(expectedBook1).size());
    }
    /*
    @Test
    void findByUser() {
        assertEquals(2, jpaBook2UserRepository.findByUser(expectedUser).size());
    }

    @Test
    void findByUserAndBook2UserType() {
        assertEquals(2, jpaBook2UserRepository.findByUserAndBook2UserType(expectedUser, expectedType).size());
    }

    @Test
    void findByUserAndBook() {
        assertEquals(1, jpaBook2UserRepository.findByUserAndBook(expectedUser, expectedBook1).size());
    }

     */

}