package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Activity;
import com.goganesh.bookshop.model.repository.JpaActivityRepository;
import com.goganesh.bookshop.model.service.ActivityWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaActivityWriteRepository implements ActivityWriteRepository {

    private final JpaActivityRepository jpaActivityRepository;

    @Override
    public void save(Activity activity) {
        jpaActivityRepository.save(activity);
    }
}
