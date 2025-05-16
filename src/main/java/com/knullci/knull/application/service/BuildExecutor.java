package com.knullci.knull.application.service;

import com.knullci.knull.infrastructure.persistence.entity.Build;

public interface BuildExecutor {
    void runBuild(Build build);
}
