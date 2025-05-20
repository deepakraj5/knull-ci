package com.knullci.necrosword.application.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class KnullStage {

    private String name;
    private String command;
    private Map<String, KnullStage> subStages;

}
