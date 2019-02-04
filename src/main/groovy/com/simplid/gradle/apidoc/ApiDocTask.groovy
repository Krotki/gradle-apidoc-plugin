package com.simplid.gradle.apidoc

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.gradle.util.CollectionUtils

class ApiDocTask extends DefaultTask {

    private static
    final boolean IS_WINDOWS = System.getProperty('os.name').toLowerCase().contains('windows')

    final static String DEFAULT_CONFIG_FILE = "apidoc.json"

    private Object inputDir = "$project.projectDir/src/main"
    private Object outputDir = "$project.buildDir/resources/main/static/doc"
    private List<Object> include = []
    private List<Object> exclude = []
    private Object template = ""
    private Object configDir = ""

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
    @InputFile
    File getConfigFile() {
        if (configDir) {
            return project.file(configDir.toString() + "/" + DEFAULT_CONFIG_FILE)
        }
        return null
    }

    String getConfigDir() {
        return configDir
    }

    void setConfigDir(Object config) {
        this.configDir = config
    }

    @TaskAction
    void generateDocumentation() {

        logger.info "apidoc properties: ${["inputDir": inputDir, "outputDir": outputDir, "configDir": configDir, "template": template, "include": include, "exclude": exclude]}"

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

            args('-i', "'$inputDir'")
            args('-o', "'$outputDir'")
            if (configDir) {
                args('-c', "'$configDir'")
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
