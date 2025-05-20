package com.knullci.knull.application.service;

import com.knullci.knull.application.dto.LogMessage;

import java.util.List;

public interface LogService {
    void streamLogsByBuildId(Integer buildId);
}
