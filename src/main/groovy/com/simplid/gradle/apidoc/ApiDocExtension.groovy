package com.simplid.gradle.apidoc

class ApiDocExtension {

    Object exec = null
    Object inputDir = null
    Object outputDir = null
    List<Object> include = []
    List<Object> exclude = []
    Object template = null
    Object configDir = null
    boolean generateConfig = true

    void include(Object... include) {
        this.include.addAll(include)
    }

    void exclude(Object... exclude) {
        this.exclude.addAll(exclude)
    }
}
