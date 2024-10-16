package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Activity;

import java.time.LocalDate;
import java.util.Optional;

public interface ActivityReadRepository {

    Optional<Activity> findByNameAndDate(String activity, LocalDate today);
}
