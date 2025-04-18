package dev.isxander.modstitch.base.loom

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dev.isxander.modstitch.base.AppendMixinDataTask
import dev.isxander.modstitch.util.Side

abstract class FMJAppendMixinDataTask : AppendMixinDataTask() {
    override fun applyModificationsToFile(fileExtension: String, contents: String): String {
        if (fileExtension != "json") error("Invalid file extension: $fileExtension")

        val gson = GsonBuilder().setPrettyPrinting().create()

        val json = gson.fromJson(contents, JsonObject::class.java)
        // NEVER read the mixins from the json object, you will duplicate them with cached files
        // this comes at the cost of requiring NO mixin configs from being static in the json file
        val mixins = JsonArray().also { json.add("mixins", it) }

        mixinConfigs.get().forEach {
            val obj = JsonObject()
            obj.addProperty("config", it.config)
            obj.addProperty("environment", when (it.side) {
                Side.Both -> "*"
                Side.Client -> "client"
                Side.Server -> "server"
            })
            mixins.add(obj)
        }

        return gson.toJson(json)
    }
}