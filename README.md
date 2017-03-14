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
| include   | '".*\\\\.(clj&#124;cls&#124;coffee&#124;cpp&#124;cs&#124;dart&#124;erl&#124;exs?&#124;go&#124;groovy&#124;ino?&#124;java&#124;js&#124;jsx&#124;kt&#124;litcoffee&#124;lua&#124;p&#124;php?&#124;pl&#124;pm&#124;py&#124;rb&#124;scala&#124;ts&#124;vue)$"' |
| exclude   | "" |
| template  | "" |
| config    | "" |
