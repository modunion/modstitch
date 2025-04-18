package dev.isxander.modstitch.publishing.loom

import dev.isxander.modstitch.publishing.PublishingCommonImpl
import dev.isxander.modstitch.publishing.msPublishing
import net.fabricmc.loom.task.RemapJarTask
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.named

class PublishingLoomImpl : PublishingCommonImpl<Nothing>() {
    override fun apply(target: Project) {
        super.apply(target)

        target.msPublishing.mpp {
            modLoaders.add("fabric")
        }
    }
}