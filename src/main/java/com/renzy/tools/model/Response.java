package com.renzy.tools.model;

import lombok.Data;

import java.util.Map;

@Data
public class Response {
    private String description;
    private Map<String, String> schema;
}
