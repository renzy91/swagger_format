package com.renzy.tools.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Post {
    private List<String> tags;
    private String summary;
    private String description;
    private String operationId;
    private List<String> consumes;
    private List<String> produces;
    private List<Parameter> parameters;
    private Map<String, Response> responses;

}
