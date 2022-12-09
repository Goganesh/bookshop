package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT uc.user FROM UserContact uc " +
            "WHERE uc.approved = true and uc.contactType = 'EMAIL' and uc.contact = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT uc.user FROM UserContact uc " +
            "WHERE uc.approved = true and uc.contactType = 'PHONE' and uc.contact = :phone")
    Optional<User> findByPhone(@Param("phone") String phone);

    Optional<User> findByHash(String hash);
}
