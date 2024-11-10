package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.ActivityCounterService;
import com.goganesh.bookshop.model.domain.Activity;
import com.goganesh.bookshop.model.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;

@Transactional
@Service
public class ActivityCounterServiceImpl implements ActivityCounterService {

    private final ActivityRepository activityRepository;

    public ActivityCounterServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public void incrementCounterByActivityName(String activityName) {
        var today = LocalDate.now();
        activityRepository.findByNameAndDate(activityName, today)
                .ifPresentOrElse(
                        activity -> {
                            var count = activity.getCount() + 1;
                            activity.setCount(count);
                            activityRepository.save(activity);
                        }, () -> activityRepository.save(
                                Activity.builder()
                                        .name(activityName)
                                        .count(1)
                                        .date(LocalDate.now())
                                        .build())
                );
    }
}
