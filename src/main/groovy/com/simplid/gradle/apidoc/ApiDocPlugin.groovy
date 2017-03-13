package com.simplid.gradle.apidoc

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.UnknownTaskException
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.language.jvm.tasks.ProcessResources

class ApiDocPlugin implements Plugin<Project> {

    private static Logger logger = Logging.getLogger(ApiDocPlugin)

    void apply(Project project) {

        ApiDocExtension apidoc = project.extensions.create('apidoc', ApiDocExtension)

        addTaskDependencies(project)

        project.afterEvaluate {
            project.task('apidoc').with {
                group = 'documentation'
                description = 'Generates the REST API documentation with ApiDocJS (requires apidoc to be installed).'

                apidoc.inputDir = apidoc.inputDir ?: "$project.projectDir/src/main"
                apidoc.outputDir = apidoc.outputDir ?: "$project.buildDir/resources/main/static/doc"
                if (!apidoc.include) {
                    apidoc.include = ['".*\\\\.(clj|cls|coffee|cpp|cs|dart|erl|exs?|go|groovy|ino?|java|js|jsx|kt|litcoffee|lua|p|php?|pl|pm|py|rb|scala|ts|vue)$"']
                }

                logger.info "apidoc input dir: $apidoc.inputDir"
                logger.info "apidoc output dir: $apidoc.outputDir"

                inputs.dir(apidoc.inputDir)
                outputs.dir(apidoc.outputDir)

                doLast {
                    //project.delete(apidoc.outputDir)

                    project.exec {
                        if (System.getProperty('os.name').toLowerCase().contains('windows')) {
                            executable('apidoc.cmd')
                        } else {
                            executable('apidoc')
                        }

                        args('-i', apidoc.inputDir)
                        args('-o', apidoc.outputDir)
                        apidoc.include.each {
                            args('-f', it)
                        }
                        apidoc.exclude.each {
                            args('-e', it)
                        }
                        if (apidoc.config) {
                            args('-c', apidoc.config)
                        }
                        if (apidoc.template) {
                            args('-t', apidoc.template)
                        }
                    }
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
