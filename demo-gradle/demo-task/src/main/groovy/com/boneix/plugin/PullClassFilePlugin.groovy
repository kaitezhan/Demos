package com.boneix.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by rzhang on 5/22/2017.
 */
class PullClassFilePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        println "PostPropertiesTask's work ready to start"
        project.extensions.create('configArgs', PostProperties)
        project.task('pullTask', type: PostPropertiesTask)
        println "PostPropertiesTask's work had finish"
    }
}
