package com.frezrik.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class ComBuild implements Plugin<Project> {


    @Override
    void apply(Project project) {
        println('##### apply ')
    }
}