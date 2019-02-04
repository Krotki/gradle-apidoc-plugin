gradle-apidoc-plugin
====================

Generates REST API documentation using [apidocjs](http://apidocjs.com/).

This plugin can be used to generate documentation that can be served by 
compiled application.

Requirements
------------

**_This plugin assumes that you have apidoc installed on your system!_**


Please install [nodejs](https://nodejs.org/en/download/) and [apidocjs](http://apidocjs.com/):

`npm install apidoc -g`

> **HINT:** you may need `sudo`

Configuration
-------------

```groovy
plugins {
    id "com.simplid.gradle.apidoc" version "0.0.1"
}
 
apidoc {
    inputDir "src/main/java/some/package/controllers"
    outputDir "doc"
    include ".groovy", ".java"
    include ".kt"
    exclude ".scala"
    exclude ".js"
    template "/path/to/template"
    config "config/apidoc.json"
}
```

| Param     | Default |
| --------- | ------- |
| inputDir  | "$project.projectDir/src/main" |
| outputDir | "$project.buildDir/resources/main/static/doc" |
| include   | "" |
| exclude   | "" |
| template  | "" |
| config    | "" |
