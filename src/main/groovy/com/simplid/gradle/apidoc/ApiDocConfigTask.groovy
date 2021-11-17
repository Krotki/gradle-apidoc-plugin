package com.simplid.gradle.apidoc

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class ApiDocConfigTask extends DefaultTask {

    @Input
    String projectName = project.name

    @Input
    Object projectVersion = project.version

    @Input
    String projectDescription = project.description

    @OutputFile
    String configFile = null

    File getConfigFile() {
        return project.file(configFile ?: "$project.buildDir/apidoc/apidoc.json")
    }

    @TaskAction
    void generateApiDocConfig() {
        getConfigFile().write("""\
{
  "name": "$projectName",
  "version": "${projectVersion == 'unspecified' ? '1.0.0' : projectVersion}",
  "description": "$projectDescription"
}"""
        )
    }
}
