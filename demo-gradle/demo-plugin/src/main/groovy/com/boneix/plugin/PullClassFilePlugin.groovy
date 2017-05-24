package com.boneix.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by rzhang on 5/22/2017.
 */
class PullClassFilePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        println "PullClassFilePlugin's work ready to start"
        project.extensions.create("postProperties", PostProperties)

        project.task('pullClassFile') {
            doLast{
                println "PullClassFilePlugin's work is doing"
            }
//            HTTPBuilder builder = new HTTPBuilder()
//            Object o = builder.request(Method.POST, ContentType.JSON, null)
//            println(o.toString())
        }
        println "PullClassFilePlugin's work had finish"
    }
}
