gradle-apidoc-plugin
====================

Generates REST API documentation using [apidocjs](http://apidocjs.com/).

This plugin can be used to generate documentation that can be served by 
compiled application.

Requirements
------------

* [nodejs](https://nodejs.org/en/download/)
* [apidocjs](http://apidocjs.com/)
    * global installation 
        * `npm install apidoc -g` (you may need `sudo`)
    * local installation
        * in your project folder type `npm install apidoc` (this will create *node_modules* folder with all dependencies)

Configuration
-------------

```groovy
plugins {
    id "com.simplid.gradle.apidoc" version "0.0.2"
}
 
apidoc {
    inputDir "src/main/java/some/package/controllers"
    outputDir "$project.buildDir/resources/main/doc"
    include ".groovy", ".java"
    include ".kt"
    exclude ".scala"
    exclude ".js"
    template "/path/to/template"
    configDir "custom/config"
    generateConfig = false
}
```

Param          | Default
-------------- | -------
inputDir       | "$project.projectDir/src/main"
outputDir      | "$project.buildDir/resources/main/static/doc"
include        | '".*\\\\.(clj\|cls\|coffee\|cpp\|cs\|dart\|erl\|exs?\|go\|groovy\|ino?\|java\|js\|jsx\|kt\|litcoffee\|lua\|p\|php?\|pl\|pm\|py\|rb\|scala\|ts\|vue)$"'
exclude        | ""
configDir      | ""
template       | ""
generateConfig | true
