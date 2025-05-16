package com.knullci.knull.application.service;

import com.knullci.knull.domain.model.Build;

import java.util.List;

public interface BuildService {

    void buildNext();
    List<Build> getBuilds(String status);
    void save(Build build);

}
