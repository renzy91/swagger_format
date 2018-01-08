package com.renzy.tools;

import com.alibaba.fastjson.JSON;
import com.renzy.tools.model.*;
import com.renzy.tools.model.vo.DefinitionVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        Swagger swagger = main.getSwagger("swagger.json");
        main.generateApi(swagger, "");

    }

    /**
     * 读取swagger.json文件,转换为swagger对象
     * @return
     */
    public Swagger getSwagger(String path) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder swaggerStrBuilder = new StringBuilder();
        try {
            String str = bufferedReader.readLine();
            while (str != null) {
                swaggerStrBuilder.append(str);
                str = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Swagger swagger = JSON.parseObject(swaggerStrBuilder.toString().replaceAll("#/definitions/", ""), Swagger.class);
        return swagger;
    }

    /**
     * 生成api文档
     * @param swagger
     * @param dist 生成的文档路径
     */
    public void generateApi(Swagger swagger, String dist) throws IOException, TemplateException {
        String basePath = swagger.getBasePath();
        List<Tag> tags = swagger.getTags();
        Map<String, Path> paths = swagger.getPaths();
        Map<String, Definition> definitions = swagger.getDefinitions();

        Map<String, List<Map<String, Path>>> parameterMap = handlerPaths(paths);
        HashMap<String, List<DefinitionVo>> definitionVoMap = handlerDefinitions(definitions);
        definitionVoMap.put("HfqResponse", null);

        Configuration configuration = new Configuration();
//        configuration.setClassForTemplateLoading(Main.class, );
//        configuration.setDirectoryForTemplateLoading(new File("/com/renzy/tools/ftl"));

        Template t = configuration.getTemplate("template.ftl");

        for (Tag tag : tags) {
            String description = tag.getDescription();
            String name = tag.getName();
            List<Map<String, Path>> pathList = parameterMap.get(name);

            File file = new File("api/" + description + ".doc");
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

            HashMap<String, Object> rootMap = new HashMap<>();
            rootMap.put("pathList", pathList);
            rootMap.put("basePath", basePath);
            rootMap.put("definitions", definitionVoMap);
            rootMap.put("tag", tag);
            t.process(rootMap, osw);

            osw.flush();
            osw.close();


        }

    }

    private HashMap<String, List<DefinitionVo>> handlerDefinitions(Map<String, Definition> definitions) {
        HashMap<String, List<DefinitionVo>> definitionVoMap = new HashMap<>();

        for (Map.Entry<String, Definition> definition : definitions.entrySet()) {
            List<DefinitionVo> definitionVoList = new ArrayList<>();

            ArrayList<String> refList = new ArrayList<>();
            refList.add(definition.getKey());
            findRef(definition.getValue(), refList, definitions);

            for (String ref : refList) {
                Definition def = definitions.get(ref);
                if (def == null) {
                    continue;
                }
                Map<String, Parameter> properties = def.getProperties();
                if (CollectionUtils.isEmpty(properties)) {
                    continue;
                }
                List<String> required = def.getRequired();
                String type = def.getType();

                DefinitionVo definitionVo = new DefinitionVo();
                ArrayList<DefinitionVo.Parameter> parameterList = new ArrayList<>();
                DefinitionVo.Parameter para;

                for (Map.Entry<String, Parameter> parameter : properties.entrySet()) {
                    para = new DefinitionVo.Parameter();

                    String parameterName = parameter.getKey();
                    Map<String, String> items = parameter.getValue().getItems();
                    String typee = parameter.getValue().getType();
                    String description = parameter.getValue().getDescription();

                    para.setPropertyName(parameterName);
                    para.setType(typee);
                    para.setDescription(description);
                    para.setRequired(CollectionUtils.isEmpty(required) ? false : required.contains(parameterName));
                    if (!CollectionUtils.isEmpty(items)) {
                        para.setDescription(items.get("$ref"));
                    }

                    parameterList.add(para);
                }

                definitionVo.setName(ref);
                definitionVo.setParameters(parameterList);
                definitionVoList.add(definitionVo);
            }

            definitionVoMap.put(definition.getKey(), definitionVoList);
        }


        return definitionVoMap;
    }

    private void findRef(Definition value, ArrayList<String> refList, Map<String, Definition> definitions) {
        Map<String, Parameter> properties = value.getProperties();
        if (CollectionUtils.isEmpty(properties)) {
            return;
        }
        for (Map.Entry<String, Parameter> parameterMap : properties.entrySet()) {
            Parameter parameter = parameterMap.getValue();
            Map<String, String> items = parameter.getItems();
            if (CollectionUtils.isEmpty(items)) {
                continue;
            }
            refList.add(items.get("$ref"));
            Definition def = definitions.get(items.get("$ref"));
            if (def != null) {
                findRef(def, refList, definitions);
            }
        }
    }

    /**
     * 将paths处理为HashMap<String, List<Map<String, Path>>>形式；
     * key:tag
     * value:path
     * @param paths
     * @return
     */
    private HashMap<String, List<Map<String, Path>>> handlerPaths(Map<String, Path> paths) {
        HashMap<String, List<Map<String, Path>>> pathMap = new HashMap<String, List<Map<String, Path>>>();
        for (Map.Entry<String, Path> map : paths.entrySet()) {
            Path path = map.getValue();
            String tag = path.getPost().getTags().get(0);
            if (pathMap.containsKey(tag)) {
                List<Map<String, Path>> pathList = pathMap.get(tag);
                HashMap<String, Path> copyPath = new HashMap<String, Path>();
                copyPath.put(map.getKey(), path);
                pathList.add(copyPath);
                continue;
            }

            List<Map<String, Path>> pathList = new ArrayList<Map<String, Path>>();
            HashMap<String, Path> copyPath = new HashMap<String, Path>();
            copyPath.put(map.getKey(), path);
            pathList.add(copyPath);
            pathMap.put(tag, pathList);
        }
        return pathMap;
    }


}
