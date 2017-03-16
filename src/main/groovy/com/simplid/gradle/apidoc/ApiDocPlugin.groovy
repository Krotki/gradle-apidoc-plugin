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
            project.task('apidoc',
                    type: ApiDocTask,
                    group: 'documentation',
                    description: 'Generates the REST API documentation with ApiDocJS (requires apidoc to be installed)'
            ) { task ->

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

                if (apiDocExtension.config) {
                    task.config = apiDocExtension.config
                }

                if (apiDocExtension.template) {
                    task.template = apiDocExtension.template
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
