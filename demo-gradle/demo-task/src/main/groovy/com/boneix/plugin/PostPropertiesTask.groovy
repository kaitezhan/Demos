package com.boneix.plugin

import groovy.json.JsonOutput
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils
import org.gradle.internal.impldep.org.apache.http.HttpResponse
import org.gradle.internal.impldep.org.apache.http.client.HttpClient
import org.gradle.internal.impldep.org.apache.http.client.methods.HttpPost
import org.gradle.internal.impldep.org.apache.http.entity.StringEntity
import org.gradle.internal.impldep.org.apache.http.impl.client.HttpClients

/**
 * Created by rzhang on 2017/5/22.
 */
class PostPropertiesTask extends DefaultTask {

    @TaskAction
    void postProperties() {
        def props = new Properties()
        def file = new File("${project.configArgs.properties}")
        def url = "${project.configArgs.url}".toString()
        def sysName = 'demoTask'
        if (file.isFile()) {
            file.withInputStream {
                stream -> props.load(stream)
            }
            for (String value : props.values()) {
                pullClassFile(url, new PostParams(sysName))
            }
        } else {
            println path + ' is not exist!'
        }
    }

    void pullClassFile(String url, PostParams postParams) {
        // step.1 请求服务器拿到文件流
        HttpPost httpPost = new HttpPost(url)
        httpPost.setEntity(new StringEntity(JsonOutput.toJson(postParams), "UTF-8"))
        HttpClient client = HttpClients.createDefault()
        HttpResponse response = client.execute(httpPost)
        InputStream inputStream = response.getEntity().getContent()
        // step.2 将文件流保存到本地
        def jar = new File("lib/" + postParams.getSysName() + ".jar")
        FileUtils.copyInputStreamToFile(inputStream, jar)
    }
}
