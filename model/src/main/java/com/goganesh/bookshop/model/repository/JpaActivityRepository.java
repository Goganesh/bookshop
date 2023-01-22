package com.goganesh.bookshop.model.repository;


import com.goganesh.bookshop.model.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface JpaActivityRepository extends JpaRepository<Activity, Integer> {
    Optional<Activity> findByNameAndDate(String activity, LocalDate today);
}
