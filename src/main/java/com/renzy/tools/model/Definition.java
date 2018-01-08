package com.renzy.tools.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Definition {
    private String type;
    private List<String> required;
    private Map<String, Parameter> properties;
}
