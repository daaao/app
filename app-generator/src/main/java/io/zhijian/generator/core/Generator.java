package io.zhijian.generator.core;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.zhijian.utils.PropertyReader;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;

/**
 * @author Hao
 * @create 2017-03-27
 */
public class Generator {

    private static String TEMPLATE_PATH;
    private static String OUTPUT_PATH;
    private static String INPUT_PATH;
    private static String DAO_PACKAGE;
    private static String SERVICE_PACKAGE;
    private static String CONTROLLER_PACKAGE;
    private static String MODEL_PACKAGE;
    private static String BASE_PACKAGE;
    private static String PK_TYPE;

    private static PropertyReader reader;

    static {
        reader = new PropertyReader("config.properties");

        TEMPLATE_PATH = reader.getProperty("template.path");
        OUTPUT_PATH = reader.getProperty("output.path");
        INPUT_PATH = reader.getProperty("input.path");
        DAO_PACKAGE = reader.getProperty("dao.package");
        SERVICE_PACKAGE = reader.getProperty("service.package");
        CONTROLLER_PACKAGE = reader.getProperty("controller.package");
        MODEL_PACKAGE = reader.getProperty("model.package");
        BASE_PACKAGE = reader.getProperty("base.package");
        PK_TYPE = reader.getProperty("pk.type");
    }

    private static ConfigBuilder.Config config;
    private Configuration cfg;
    private List<File> modelFiles;

    public Generator() {
    }

    private void initConfig() {
        ConfigBuilder builder = new ConfigBuilder().newBuilder();
        config = builder
                .setTemplatePath(TEMPLATE_PATH)
                .setOutputPath(OUTPUT_PATH)
                .setInputPath(INPUT_PATH)
                .setDaoPackage(DAO_PACKAGE)
                .setServicePackage(SERVICE_PACKAGE)
                .setControllerPackage(CONTROLLER_PACKAGE)
                .setModelPackage(MODEL_PACKAGE)
                .setBasePackage(BASE_PACKAGE)
                .build();
    }

    public void build() throws Exception {
        initConfig();
        cfg = getConfiguration();
        modelFiles = getModelFiles();

        generateDao();
        generateDaoImpl();
        generateService();
        generateServiceImpl();
        generateColtroller();
        generateRequest();
        generateResponse();
    }

    private void generate(String templateName, String outputPath, String prefix, String suffix) throws Exception {
        File path = new File(outputPath);
        path.mkdirs();

        for (File modelFile : modelFiles) {
            if (modelFile.getName().lastIndexOf(".java") > -1) {
                Template t = cfg.getTemplate(templateName);
                Map<String, String> bindingMap = getBindingMap(modelFile);
                FileOutputStream fileOutputStream = new FileOutputStream(outputPath + "/" + prefix + bindingMap.get("modelName") + suffix);// 生成的文件路径
                Writer writer = new OutputStreamWriter(fileOutputStream, "UTF-8");
                t.process(bindingMap, writer);

                System.out.println("生成【" + bindingMap.get("modelName") + suffix + "】");
            }
        }
    }

    private void generateDao() throws Exception {
        System.out.println("准备生成DAO接口......");
        generate("dao.ftl", config.getDaoPath(), "I", "Dao.java");
        System.out.println("所有DAO接口生成完毕！");
        System.out.println("-------------------------------------");
    }

    private void generateDaoImpl() throws Exception {
        System.out.println("准备生成DAO实现类......");
        generate("daoImpl.ftl", config.getDaoImplPath(), "", "Dao.java");
        System.out.println("所有DAO实现类生成完毕！");
        System.out.println("-------------------------------------");
    }

    private void generateService() throws Exception {
        System.out.println("准备生成Service接口......");
        generate("service.ftl", config.getServicePath(), "I", "Service.java");
        System.out.println("所有Service接口生成完毕！");
        System.out.println("-------------------------------------");
    }

    private void generateServiceImpl() throws Exception {
        System.out.println("准备生成Service实现类......");
        generate("serviceImpl.ftl", config.getServiceImplPath(), "", "Service.java");
        System.out.println("所有Service实现类生成完毕！");
        System.out.println("-------------------------------------");
    }

    private void generateColtroller() throws Exception {
        System.out.println("准备生成Controller类......");
        generate("controller.ftl", config.getControllerPath(), "", "Controller.java");
        System.out.println("所有Controller类生成完毕！");
        System.out.println("-------------------------------------");
    }

    private void generateRequest() throws Exception {
        System.out.println("准备生成接收参数的Request类......");
        generate("request.ftl", config.getModelPath() + "/request/", "", "Request.java");
        System.out.println("所有Request类生成完毕！");
        System.out.println("-------------------------------------");
    }

    private void generateResponse() throws Exception {
        System.out.println("准备生成响应参数的Response类......");
        generate("response.ftl", config.getModelPath() + "/response/", "", "Response.java");
        System.out.println("所有Response类生成完毕！");
        System.out.println("-------------------------------------");
    }

    private static Configuration getConfiguration() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setDirectoryForTemplateLoading(new File(config.getTemplatePath()));
        return cfg;
    }

    private List<File> getModelFiles(String... destinationModelNames) {
        List<File> modelFileList = null;
        File file = new File(config.getInputPath());
        File[] modelFiles = file.listFiles();// model文件数组
        if (modelFiles != null) {
            modelFileList = Arrays.asList(modelFiles);
            List<File> generatorFileList = new ArrayList<File>();
            if (destinationModelNames != null && destinationModelNames.length > 0) {// 如果要指定某些model来生成dao、service、controller文件
                for (File modelFile : modelFileList) {
                    for (String mn : destinationModelNames) {
                        if (modelFile.getName().equalsIgnoreCase(mn + ".java")) {
                            generatorFileList.add(modelFile);
                        }
                    }
                }
                modelFileList = generatorFileList;
            }
        }
        return modelFileList;
    }

    private Map<String, String> getBindingMap(File modelFile) throws IOException {
        FileInputStream fis = new FileInputStream(modelFile);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String firstString = br.readLine();
        String entityPackageName = firstString.replace("package ", "").replace(";", "").trim();// model的包名
        br.close();
        isr.close();
        fis.close();
        String modelName = modelFile.getName().substring(0, modelFile.getName().indexOf("."));// model的名字
        Map<String, String> bindingMap = new HashMap<String, String>();
        bindingMap.put("entityPackageName", entityPackageName);
        bindingMap.put("pkType", config.getPkType());
        bindingMap.put("modelName", modelName);
        bindingMap.put("lowerModelName", StringUtils.lowerCase(modelName));
        bindingMap.put("daoPackageName", config.getDaoPackage());
        bindingMap.put("daoImplPackageName", config.getDaoImplPackage());
        bindingMap.put("servicePackageName", config.getServicePackage());
        bindingMap.put("serviceImplPackageName", config.getServiceImplPackage());
        bindingMap.put("controllerPackageName", config.getControllerPackage());
        bindingMap.put("modelPackageName", config.getModelPackage());
        bindingMap.put("basePackageName", config.getBasePackage());
        return bindingMap;
    }
}
