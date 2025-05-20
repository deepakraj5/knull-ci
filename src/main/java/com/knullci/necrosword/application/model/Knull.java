package com.knullci.necrosword.application.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Knull {

    private String name;
    private List<KnullStage> stages;

}
