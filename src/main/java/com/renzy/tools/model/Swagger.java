package com.renzy.tools.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Swagger {
    private String swagger;
    private Object info;
    private String host;
    private String basePath;
    private List<Tag> tags;
    private Map<String, Path> paths;
    private Map<String, Definition> definitions;

}
