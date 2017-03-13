package com.simplid.gradle.apidoc

class ApiDocExtension {

    private String inputDir
    private String outputDir
    private List<String> include = []
    private List<String> exclude = []
    private String template = null
    private String config = null

    String getInputDir() {
        return inputDir
    }

    void setInputDir(String inputDir) {
        this.inputDir = inputDir
    }

    String getOutputDir() {
        return outputDir
    }

    void setOutputDir(String outputDir) {
        this.outputDir = outputDir
    }

    List<String> getInclude() {
        return include
    }

    void setInclude(List<String> include) {
        this.include = include
    }

    void include(String... include) {
        this.include.addAll(include as List)
    }

    List<String> getExclude() {
        return exclude
    }

    void setExclude(List<String> exclude) {
        this.exclude = exclude
    }

    void exclude(String... exclude) {
        this.exclude.addAll(exclude as List)
    }

    String getTemplate() {
        return template
    }

    void setTemplate(String template) {
        this.template = template
    }

    String getConfig() {
        return config
    }

    void setConfig(String config) {
        this.config = config
    }
}
