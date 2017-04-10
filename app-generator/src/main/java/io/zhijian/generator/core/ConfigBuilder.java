package io.zhijian.generator.core;

/**
 * @author Hao
 * @create 2017-03-27
 */
public class ConfigBuilder {

    private Config config;

    public ConfigBuilder newBuilder() {
        config = new Config();
        return this;
    }

    public ConfigBuilder setTemplatePath(String templatePath) {
        config.setTemplatePath(templatePath);
        return this;
    }

    public ConfigBuilder setOutputPath(String outputPath) {
        config.setOutputPath(outputPath);
        return this;
    }

    public ConfigBuilder setInputPath(String inputPath) {
        config.setInputPath(inputPath);
        return this;
    }

    public ConfigBuilder setDaoPackage(String daoPackage) {
        config.setDaoPackage(daoPackage);
        return this;
    }

    public ConfigBuilder setServicePackage(String servicePackage) {
        config.setServicePackage(servicePackage);
        return this;
    }

    public ConfigBuilder setControllerPackage(String controllerPackage) {
        config.setControllerPackage(controllerPackage);
        return this;
    }

    public ConfigBuilder setModelPackage(String modelPackage) {
        config.setModelPackage(modelPackage);
        return this;
    }

    public ConfigBuilder setBasePackage(String basePackage) {
        config.setBasePackage(basePackage);
        return this;
    }

    public ConfigBuilder setPkType(String pkType) {
        config.setPkType(pkType);
        return this;
    }

    public Config build() {
        if (null == config.getTemplatePath() || config.getTemplatePath().trim().isEmpty()) {
            throw new RuntimeException("template path should not be empty");
        }

        if (null == config.getOutputPath() || config.getOutputPath().trim().isEmpty()) {
            throw new RuntimeException("output path should not be empty");
        }

        if (null == config.getDaoPackage() || config.getDaoPackage().trim().isEmpty()) {
            throw new RuntimeException("dao package should not be empty");
        }

        if (null == config.getServicePackage() || config.getServicePackage().trim().isEmpty()) {
            throw new RuntimeException("service package should not be empty");
        }

        if (null == config.getControllerPackage() || config.getControllerPackage().trim().isEmpty()) {
            throw new RuntimeException("controller package should not be empty");
        }

        if (null == config.getModelPackage() || config.getModelPackage().trim().isEmpty()) {
            throw new RuntimeException("model package should not be empty");
        }

        if (null == config.getBasePackage() || config.getBasePackage().trim().isEmpty()) {
            throw new RuntimeException("base package should not be empty");
        }

        config.setDaoPath(config.getOutputPath() + "/" + config.getDaoPackage().replaceAll("\\.", "/"));
        config.setDaoImplPackage(config.getDaoPackage() + ".impl");
        config.setDaoImplPath(config.getOutputPath() + "/" + config.getDaoImplPackage().replaceAll("\\.", "/"));
        config.setServicePath(config.getOutputPath() + "/" + config.getServicePackage().replaceAll("\\.", "/"));
        config.setServiceImplPackage(config.getServicePackage() + ".impl");
        config.setServiceImplPath(config.getOutputPath() + "/" + config.getServiceImplPackage().replaceAll("\\.", "/"));
        config.setControllerPath(config.getOutputPath() + "/" + config.getControllerPackage().replaceAll("\\.", "/"));
        config.setModelPath(config.getOutputPath() + "/" + config.getModelPackage().replaceAll("\\.", "/"));
        config.setBasePath(config.getOutputPath() + "/" + config.getBasePackage().replaceAll("\\.", "/"));

        return config;
    }


    class Config {
        private String templatePath;//模板所在目录
        private String outputPath;// 将生成的文件写入哪个目录
        private String inputPath;// 模型文件路径
        private String pkType = "Integer";// 主键类型
        private String daoPackage;// dao接口的包名
        private String daoPath;// dao生成目录
        private String daoImplPackage;// daoImpl的包名
        private String daoImplPath;// daoImpl生成目录
        private String servicePackage;// service接口的包名
        private String servicePath;// service生成目录
        private String serviceImplPackage;// serviceImpl的包名
        private String serviceImplPath;// serviceImpl生成目录
        private String controllerPackage;// controller的包名
        private String controllerPath;// controller生成目录
        private String modelPackage;// model的包名
        private String modelPath;// model生成目录
        private String basePackage;// 根包
        private String basePath;// 根目录

        public String getTemplatePath() {
            return templatePath;
        }

        public void setTemplatePath(String templatePath) {
            this.templatePath = templatePath;
        }

        public String getOutputPath() {
            return outputPath;
        }

        public void setOutputPath(String outputPath) {
            this.outputPath = outputPath;
        }

        public String getInputPath() {
            return inputPath;
        }

        public void setInputPath(String inputPath) {
            this.inputPath = inputPath;
        }

        public String getPkType() {
            return pkType;
        }

        public void setPkType(String pkType) {
            this.pkType = pkType;
        }

        public String getDaoPackage() {
            return daoPackage;
        }

        public void setDaoPackage(String daoPackage) {
            this.daoPackage = daoPackage;
        }

        public String getDaoPath() {
            return daoPath;
        }

        public void setDaoPath(String daoPath) {
            this.daoPath = daoPath;
        }

        public String getDaoImplPackage() {
            return daoImplPackage;
        }

        public void setDaoImplPackage(String daoImplPackage) {
            this.daoImplPackage = daoImplPackage;
        }

        public String getDaoImplPath() {
            return daoImplPath;
        }

        public void setDaoImplPath(String daoImplPath) {
            this.daoImplPath = daoImplPath;
        }

        public String getServicePackage() {
            return servicePackage;
        }

        public void setServicePackage(String servicePackage) {
            this.servicePackage = servicePackage;
        }

        public String getServicePath() {
            return servicePath;
        }

        public void setServicePath(String servicePath) {
            this.servicePath = servicePath;
        }

        public String getServiceImplPackage() {
            return serviceImplPackage;
        }

        public void setServiceImplPackage(String serviceImplPackage) {
            this.serviceImplPackage = serviceImplPackage;
        }

        public String getServiceImplPath() {
            return serviceImplPath;
        }

        public void setServiceImplPath(String serviceImplPath) {
            this.serviceImplPath = serviceImplPath;
        }

        public String getControllerPackage() {
            return controllerPackage;
        }

        public void setControllerPackage(String controllerPackage) {
            this.controllerPackage = controllerPackage;
        }

        public String getControllerPath() {
            return controllerPath;
        }

        public void setControllerPath(String controllerPath) {
            this.controllerPath = controllerPath;
        }

        public String getModelPackage() {
            return modelPackage;
        }

        public void setModelPackage(String modelPackage) {
            this.modelPackage = modelPackage;
        }

        public String getModelPath() {
            return modelPath;
        }

        public void setModelPath(String modelPath) {
            this.modelPath = modelPath;
        }

        public String getBasePackage() {
            return basePackage;
        }

        public void setBasePackage(String basePackage) {
            this.basePackage = basePackage;
        }

        public String getBasePath() {
            return basePath;
        }

        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }
    }
}
