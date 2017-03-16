package com.simplid.gradle.apidoc

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.gradle.util.CollectionUtils

class ApiDocTask extends DefaultTask {

    private static
    final boolean IS_WINDOWS = System.getProperty('os.name').toLowerCase().contains('windows')

    private Object inputDir = "$project.projectDir/src/main"
    private Object outputDir = "$project.buildDir/resources/main/static/doc"
    private List<Object> include = ['".*\\\\.(clj|cls|coffee|cpp|cs|dart|erl|exs?|go|groovy|ino?|java|js|jsx|kt|litcoffee|lua|p|php?|pl|pm|py|rb|scala|ts|vue)$"']
    private List<Object> exclude = []
    private Object template = null
    private Object config = null

    @InputDirectory
    File getInputDir() {
        return project.file(inputDir)
    }

    void setInputDir(Object inputDir) {
        this.inputDir = inputDir
    }

    @OutputDirectory
    File getOutputDir() {
        return project.file(outputDir)
    }

    void setOutputDir(Object outputDir) {
        this.outputDir = outputDir
    }

    @Optional
    @Input
    List<String> getInclude() {
        return CollectionUtils.stringize(include)
    }

    void setInclude(List<Object> include) {
        this.include.clear()
        this.include.addAll(include as List)
    }

    void include(Object... include) {
        this.include.addAll(include as List)
    }

    @Optional
    @Input
    List<String> getExclude() {
        return CollectionUtils.stringize(exclude)
    }

    void setExclude(List<Object> exclude) {
        this.exclude.clear()
        this.exclude.addAll(exclude as List)
    }

    void exclude(Object... exclude) {
        this.exclude.addAll(exclude as List)
    }

    @Optional
    @Input
    String getTemplate() {
        return template
    }

    void setTemplate(Object template) {
        this.template = template
    }

    @Optional
    @Input
    String getConfig() {
        return config
    }

    void setConfig(Object config) {
        this.config = config
    }

    @TaskAction
    void generateDocumentation() {

        logger.info "apidoc input dir: $inputDir"
        logger.info "apidoc output dir: $outputDir"

        String apiDocExec = "apidoc"
        if (IS_WINDOWS) {
            apiDocExec += ".cmd"
        }

        File localApiDocExec = project.file("node_modules/apidoc/bin/$apiDocExec")
        if (localApiDocExec.exists() && localApiDocExec.isFile() && localApiDocExec.canExecute()) {
            apiDocExec = localApiDocExec.getAbsolutePath()
        }

        logger.info("apidoc executable: $apiDocExec")

        project.exec {
            executable(apiDocExec)

            args('-i', inputDir)
            args('-o', outputDir)
            if (config) {
                args('-c', config)
            }
            if (template) {
                args('-t', template)
            }
            include.each {
                args('-f', it)
            }
            exclude.each {
                args('-e', it)
            }
        }
    }
}
