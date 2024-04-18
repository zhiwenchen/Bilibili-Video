package com.zhiwen.navPlugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import java.io.File
import java.io.InputStream
import java.util.zip.ZipFile

class NavTransform(project: Project) : Transform() {
    override fun getName(): String {
        return "NavTransform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        // 处理Class文件
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    // 是否支持增量编译
    override fun isIncremental(): Boolean {
        return false
    }

    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)
        val inputs = transformInvocation?.inputs ?: return
        val outputProvider = transformInvocation.outputProvider
        outputProvider.deleteAll()
        inputs.forEach {
            // 分别对输入的目录和文件进行遍历
            it.directoryInputs.forEach { dirIt ->
                handleDirectoryClasses(dirIt.file)
                // 创建一个输出目录
                val outputDir = outputProvider.getContentLocation(
                    dirIt.name,
                    dirIt.contentTypes,
                    dirIt.scopes,
                    Format.DIRECTORY
                )
                if (dirIt.file.isFile) {
                    FileUtils.copyFile(dirIt.file, outputDir)
                } else {
                    FileUtils.copyDirectory(dirIt.file, outputDir)
                }
            }
            it.jarInputs.forEach { fileIt ->
                handleJarClasses(fileIt.file)
                var jarName = fileIt.name
                val md5 = DigestUtils.md5Hex(fileIt.file.absolutePath)
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length - 4)
                }
                val outputDir = outputProvider.getContentLocation(md5 + jarName,
                    fileIt.contentTypes,
                    fileIt.scopes,
                    Format.JAR)
                FileUtils.copyFile(fileIt.file, outputDir)
            }
        }
    }

    private fun handleJarClasses(file: File) {
        // 解压Jar包
        val zipFile = ZipFile(file)
        zipFile.stream().forEach {
            if (it.name.endsWith("class",true)) {
                println("NavTransform handleJarClasses-zipEntry:${it.name}")

                zipFile.getInputStream(it).use {
                    visitClass(it)
                }
            }
        }
        zipFile.close()
    }

    private fun handleDirectoryClasses(file: File) {
        if (file.isDirectory) {
            file.listFiles()?.forEach {
                handleDirectoryClasses(file)
            }
        } else if (file.extension.endsWith("class")) {
            file.inputStream().use {
                visitClass(it)
            }
        }
    }

    private fun visitClass(inputStream: InputStream) {

    }
}