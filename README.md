gradle-apidoc-plugin
====================

This plugin can be used to generate REST documentation that can be served by 
compiled application.

Requirements
------------

* [nodejs](https://nodejs.org/en/download/)
* [apidocjs](http://apidocjs.com/)
    * global installation 
        * `npm install apidoc -g` (you may need `sudo`)
    * local installation
        * in your project folder type `npm install apidoc` (this will create *node_modules* folder with all dependencies)

Tasks
-----

#### apidoc

Generates REST API documentation using [apidocjs](http://apidocjs.com/). 

#### apidocGenConfig

Generates minimal apidoc.json configuation file from project variables (name, description, version).

Configuration
-------------

Listening below illustrates how can you use all available options.
If you agree with defaults, you can omit whole `apidoc` section.

```groovy
plugins {
    id "com.simplid.gradle.apidoc" version "0.0.3"
}
 
apidoc {
    exec "apidoc"
    inputDir "src/main/java/some/package/controllers"
    outputDir "$project.buildDir/resources/main/doc"
    include ".groovy", ".java"
    include ".kt"
    exclude ".scala"
    exclude ".js"
    template "/path/to/template"
    configDir "custom/config"
    generateConfig false
}
```

### Options:

#### exec
> default: `"apidoc"` or `"apidoc.cmd"`

Full path or name on *$path* to apidoc executable. If not specified, plugin will check if local copy of
apidoc is avaible inside `node_modules/apidoc/bin/` folder in your project, otherwise it will try to 
execute apidoc from *$path*.

#### inputDir
> default: `"src/main"`

Should point to folder where you store all your annotated files. To speed up generation, you should 
point to folder that directly contains annotated files f.i. controllers or resources package.

#### outputDir
> default: `"$project.buildDir/resources/main/static/doc"`

Where to generate output.

#### include
> default: `[]`

> example: `[".*\\\\.(clj|cls|coffee|cpp|cs|dart|erl|exs?|go|groovy|ino?|java|js|jsx|kt|litcoffee|lua|p|php?|pl|pm|py|rb|scala|ts|vue)$"]`

RegEx filter to select files that should be parsed. Multiple includes can be used.

#### exclude
> default: `[]`

RegEx filter to select files/dirs that should not be parsed. Multiple excludes can be used.

#### configDir
> default: `""` or `"$project.buildDir/apidoc"` if **generateConfig** flag is set

Path to directory containing config file `apidoc.json`. This setting is used by both tasks. 

#### template
> default: `""`

Path to directory containing apidoc template.

#### generateConfig
> default: `true`

When this flag is set, task **apidoc** will depend on **apidocGenConfig**. To use tasks 
independently, set it to `false`.
