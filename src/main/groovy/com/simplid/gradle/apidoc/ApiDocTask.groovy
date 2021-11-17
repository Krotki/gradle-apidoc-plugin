package com.simplid.gradle.apidoc

import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.gradle.process.ExecSpec
import org.gradle.util.CollectionUtils

class ApiDocTask extends DefaultTask {

    private static final boolean IS_WINDOWS = System.getProperty('os.name').toLowerCase().contains('windows')

    private Object inputDir = "$project.projectDir/src/main"
    private Object outputDir = "$project.buildDir/resources/main/static/doc"
    private List<Object> include = []
    private List<Object> exclude = []
    private Object template = ""
    private Object configFile = ""
    private Object exec = ""

    @Input
    Object getExec() {
        return exec
    }

    void setExec(Object exec) {
        this.exec = exec
    }

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

    @InputFile
    File getConfigFile() {
        return configFile
    }

    void setConfigFile(Object configFile) {
        this.configFile = configFile
    }

    @TaskAction
    void generateDocumentation() {

        logger.info "apidoc properties: ${["exec": exec, "inputDir": inputDir, "outputDir": outputDir, "configFile": configFile, "template": template, "include": include, "exclude": exclude]}"

        if (!exec) {
            exec = "apidoc"
            if (IS_WINDOWS) {
                exec += ".cmd"
            }

            File localApiDocExec = project.file("node_modules/apidoc/bin/$exec")
            if (localApiDocExec.exists() && localApiDocExec.isFile() && localApiDocExec.canExecute()) {
                exec = localApiDocExec.getAbsolutePath()
            }
        }

        logger.info("apidoc executable: $exec")

        project.exec({ execSpec ->
            execSpec.executable(exec)

            execSpec.args('-i', inputDir)
            execSpec.args('-o', outputDir)
            if (configFile) {
                execSpec.args('-c', configFile)
            }
            if (template) {
                execSpec.args('-t', template)
            }
            include.each {
                execSpec.args('-f', it)
            }
            exclude.each {
                execSpec.args('-e', it)
            }
        } as Action<? super ExecSpec>)
    }
}
