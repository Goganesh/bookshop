package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Activity;
import com.goganesh.bookshop.model.repository.JpaActivityRepository;
import com.goganesh.bookshop.model.service.ActivityReadRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
public class JpaActivityReadRepository implements ActivityReadRepository {

    private final JpaActivityRepository jpaActivityRepository;

    @Override
    public Optional<Activity> findByNameAndDate(String activity, LocalDate today) {
        return jpaActivityRepository.findByNameAndDate(activity, today);
    }
}
