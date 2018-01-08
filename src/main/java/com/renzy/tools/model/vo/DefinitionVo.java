package com.renzy.tools.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class DefinitionVo {
    private String name;
    private List<Parameter> parameters;

    @Data
    public static class Parameter {
        private String propertyName;
        private String type;
        private String description;
        private Boolean required;
    }


}
