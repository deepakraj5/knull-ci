package com.knullci.knull.application.service;

import com.knullci.knull.domain.model.Stage;

import java.util.List;

public interface StageService {
    List<Stage> getAllStageByBuildId(Integer buildId);
}
