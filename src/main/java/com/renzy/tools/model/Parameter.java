package com.renzy.tools.model;

import lombok.Data;

import java.util.Map;

@Data
public class Parameter {
    private String name;
    private String in;
    private String description;
    private String required;
    private String type;
    private String format;
    private Map<String, String> items;
}
