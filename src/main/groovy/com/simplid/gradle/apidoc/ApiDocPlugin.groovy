package com.simplid.gradle.apidoc

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.UnknownTaskException
import org.gradle.language.jvm.tasks.ProcessResources

class ApiDocPlugin implements Plugin<Project> {

    void apply(Project project) {

        ApiDocExtension apiDocExtension = project.extensions.create('apidoc', ApiDocExtension)

        addTaskDependencies(project)

        project.afterEvaluate {

            project.task('apidocGenConfig',
                    type: ApiDocConfigTask,
                    group: 'documentation',
                    description: 'Generates apidoc\'s minimal configuration file from project variables'
            ) { task ->

                if (apiDocExtension.configDir) {
                    task.configDir = apiDocExtension.configDir
                }
            }

            project.task('apidoc',
                    type: ApiDocTask,
                    group: 'documentation',
                    description: 'Generates the REST API documentation with ApiDocJS (requires apidoc to be installed)'
            ) { task ->

                logger.info "apidoc extension props: ${["exec": apiDocExtension.exec, "inputDir": apiDocExtension.inputDir, "outputDir": apiDocExtension.outputDir, "config": apiDocExtension.configDir, "template": apiDocExtension.template, "include": apiDocExtension.include, "exclude": apiDocExtension.exclude]}"

                if (apiDocExtension.exec) {
                    task.exec = apiDocExtension.exec
                }

                if (apiDocExtension.inputDir) {
                    task.inputDir = apiDocExtension.inputDir
                }

                if (apiDocExtension.outputDir) {
                    task.outputDir = apiDocExtension.outputDir
                }

                if (apiDocExtension.include) {
                    task.include = apiDocExtension.include
                }

                if (apiDocExtension.exclude) {
                    task.exclude = apiDocExtension.exclude
                }

                if (apiDocExtension.configDir) {
                    task.configDir = apiDocExtension.configDir
                }

                if (apiDocExtension.generateConfig && !apiDocExtension.configDir) {
                    task.configDir = new File("$project.buildDir/apidoc/")
                }

                if (apiDocExtension.template) {
                    task.template = apiDocExtension.template
                }

                if (apiDocExtension.generateConfig) {
                    task.dependsOn('apidocGenConfig')
                }
            }
        }
    }

    private static void addTaskDependencies(Project project) {
        try {
            Task t = project.tasks.getByName('processResources')
            if (t instanceof ProcessResources) {
                t.dependsOn 'apidoc'
            }
        } catch (UnknownTaskException ignored) {
            project.tasks.whenTaskAdded { Task t ->
                if (t.name == 'processResources' && t instanceof ProcessResources) {
                    t.dependsOn 'apidoc'
                }
            }
        }
    }
}
