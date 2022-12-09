package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.ActivityCounterService;
import com.goganesh.bookshop.model.domain.Activity;
import com.goganesh.bookshop.model.service.ActivityReadRepository;
import com.goganesh.bookshop.model.service.ActivityWriteRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
public class ActivityCounterServiceImpl implements ActivityCounterService {
    private final ActivityWriteRepository activityWriteRepository;
    private final ActivityReadRepository activityReadRepository;

    @Override
    public void incrementCounterByActivityName(String activityName) {
        LocalDate today = LocalDate.now();
        Activity activity = activityReadRepository.findByActivityAndDate(activityName, today).orElse(null);

        if (Objects.nonNull(activity)) {
            int count = activity.getCount() + 1;
            activity.setCount(count);
        } else {
            activity = Activity.builder()
                    .activity(activityName)
                    .count(1)
                    .date(LocalDate.now())
                    .build();
        }

        activityWriteRepository.save(activity);
    }
}
