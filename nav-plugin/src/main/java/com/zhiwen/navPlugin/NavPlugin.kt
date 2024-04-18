package com.zhiwen.navPlugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin

class NavPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        //只能用在APP模块
        project.plugins.findPlugin(ApplicationPlugin::class.java)

        val extensions = project.extensions.findByType(BaseExtension::class.java)
        extensions?.registerTransform(NavTransform(project))
    }
}