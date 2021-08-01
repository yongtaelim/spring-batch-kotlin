package me.practice.kotlinbatch.common.slack.utils

import org.slf4j.MDC

/**
 * httpRequest가 존재하는 동안 데이터를 유지시키기 위한 유틸
 * http://logback.qos.ch/manual/mdc.html
 *
 */
class MDCUtils {

    companion object {
        private val mdc = MDC.getMDCAdapter()

        /**
         * The constant HEADER_MAP_MDC.
         */
        val HEADER_MAP_MDC = "HEADER_MAP_MDC"

        /**
         * The constant PARAMETER_MAP_MDC.
         */
        val PARAMETER_MAP_MDC = "PARAMETER_MAP_MDC"

        /**
         * The constant AGENT_DETAIL_MDC.
         */
        val AGENT_DETAIL_MDC = "AGENT_DETAIL_MDC"

        /**
         * Set.
         *
         * @param key   the key
         * @param value the value
         */
        operator fun set(key: String, value: String) {
            mdc.put(key, value)
        }

        /**
         * Get string.
         *
         * @param key the key
         * @return the string
         */
        operator fun get(key: String): String? {
            return mdc[key]
        }
    }

}