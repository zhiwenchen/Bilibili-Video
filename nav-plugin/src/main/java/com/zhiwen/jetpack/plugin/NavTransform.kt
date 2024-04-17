package com.zhiwen.jetpack.plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.zip.ZipFile

class NavTransform(project: Project) : Transform() {

    override fun getName(): String {
        return "NavTransform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)
        val inputs = transformInvocation?.inputs ?: return
        val outputProvider = transformInvocation.outputProvider
        outputProvider.deleteAll()
        inputs.forEach {
            //1. 对inputs -->directory-->class 文件进行遍历
            //2 .对inputs -->jar-->class 文件进行遍历
            it.directoryInputs.forEach { it ->
                handleDirectoryClasses(it.file)
                val outputDir = outputProvider.getContentLocation(
                    it.name,
                    it.contentTypes,
                    it.scopes,
                    Format.DIRECTORY
                )
                if (it.file.isFile) {
                    FileUtils.copyFile(it.file, outputDir)
                } else {
                    FileUtils.copyDirectory(it.file, outputDir)
                }
            }
            it.jarInputs.forEach { it ->
                handleJarClasses(it.file)
                var jarName = it.name
                val md5 = DigestUtils.md5Hex(it.file.absolutePath)
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length - 4)
                }
                val outputDir = outputProvider.getContentLocation(
                    md5 + jarName,
                    it.contentTypes,
                    it.scopes,
                    Format.JAR
                )
                FileUtils.copyFile(it.file, outputDir)
            }
        }
    }

    private fun handleJarClasses(file: File) {
        println("NavTransform handleJarClasses:${file.name}")
        val zipFile = ZipFile(file)
        zipFile.stream().forEach {
            if (it.name.endsWith("class", true)) {
                println("NavTransform handleJarClasses-zipEntry:${it.name}")
                val inputStream = zipFile.getInputStream(it)
                visitClass(inputStream)
                inputStream.close()
            }
        }
        zipFile.close()
    }

    private fun handleDirectoryClasses(file: File) {
        if (file.isDirectory) {
            file.listFiles()?.forEach {
                handleDirectoryClasses(it)
            }
        } else if (file.extension.endsWith("class", true)) {
            val inputStream = FileInputStream(file)
            println("NavTransform handleDirectoryClasses-zipEntry:${file.name}")
            visitClass(inputStream)
            inputStream.close()
        }
    }

    private fun visitClass(inputStream: InputStream) {


    }
}