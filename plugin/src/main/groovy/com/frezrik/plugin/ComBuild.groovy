package plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class ComPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {
        println('##### apply ')
    }
}