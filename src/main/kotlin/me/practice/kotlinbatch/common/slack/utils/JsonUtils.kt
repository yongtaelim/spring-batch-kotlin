package me.practice.kotlinbatch.common.slack.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.apache.commons.text.StringEscapeUtils

/**
 * The type Json utils.
 */
class JsonUtils {

    private var gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .setLenient()
        .create()
    private var mapper: ObjectMapper = ObjectMapper()

    companion object {

        /**
         * Gets instance.
         *
         * @return the instance
         */
        private fun getInstance() = JsonUtils()

        fun getGson() = getInstance().gson

        fun getObjectMapper() = getInstance().mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

        /**
         * From json t.
         *
         * @param <T>     the type parameter
         * @param jsonStr the json str
         * @param cls     the cls
         * @return the t
        </T> */
        fun <T> fromJson(jsonStr: String, cls: Class<T>?): T {
            return getGson().fromJson(jsonStr, cls)
        }

        /**
         * To pretty json string.
         *
         * @param json the json
         * @return the string
         */
        fun toPrettyJson(json: String?): String? {
            if (json == null) return null
            return StringEscapeUtils.unescapeJava(getGson().toJson(fromJson(json, Any::class.java)))
        }

        /**
         * To ObjectMapper pretty json string
         * @param any
         * @return
         */
        @Throws(JsonProcessingException::class)
        fun toMapperPrettyJson(any: Any?): String = getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(any)
    }
}