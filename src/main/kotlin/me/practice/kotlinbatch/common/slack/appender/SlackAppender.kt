package me.practice.kotlinbatch.common.slack.appender

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.StackTraceElementProxy
import ch.qos.logback.core.UnsynchronizedAppenderBase
import ch.qos.logback.core.util.ContextUtil
import me.practice.kotlinbatch.common.slack.utils.JsonUtils
import me.practice.kotlinbatch.common.slack.utils.MDCUtils
import net.gpedro.integrations.slack.SlackApi
import net.gpedro.integrations.slack.SlackAttachment
import net.gpedro.integrations.slack.SlackField
import net.gpedro.integrations.slack.SlackMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.ObjectUtils
import java.net.SocketException
import java.net.UnknownHostException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SlackAppender(
    private var enabled: Boolean = false,
    private var token: String? = null,
    private var channel: String? = null,
    private var level: Level? = null,
    private var title: String? = null,
    private var botName: String? = null,
    // https://slackmojis.com/
    private var botEmoji: String? = null
): UnsynchronizedAppenderBase<ILoggingEvent>() {

    val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun doAppend(eventObject: ILoggingEvent?) {
        super.doAppend(eventObject)
    }

    override fun append(loggingEvent: ILoggingEvent) {
        if (loggingEvent.level.isGreaterOrEqual(level)) {
            if (enabled) {
                toSlack(loggingEvent)
            }
        }
    }

    private fun toSlack(loggingEvent: ILoggingEvent) {
        if (loggingEvent.level.isGreaterOrEqual(level)) {
            val fields: MutableList<SlackField> = ArrayList()
            val slackAttachment = SlackAttachment()
            slackAttachment.setFallback("장애 발생")
            slackAttachment.setColor("danger")
            slackAttachment.setTitle(loggingEvent.formattedMessage)

            if (!ObjectUtils.isEmpty(MDCUtils[MDCUtils.AGENT_DETAIL_MDC])) {
                val agentDetail = SlackField()
                agentDetail.setTitle("사용자 환경정보")
                agentDetail.setValue(JsonUtils.toPrettyJson(MDCUtils[MDCUtils.AGENT_DETAIL_MDC]))
                agentDetail.isShorten = false
                fields.add(agentDetail)
            }

            val date = SlackField()
            date.setTitle("발생 시간")
            date.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            date.isShorten = true
            fields.add(date)

            val hostName = SlackField()
            hostName.setTitle("호스트명")
            hostName.setValue(getHostName())
            hostName.isShorten = true
            fields.add(hostName)

            if (!ObjectUtils.isEmpty(MDCUtils[MDCUtils.HEADER_MAP_MDC])) {
                val headerInformation = SlackField()
                headerInformation.setTitle("Http Header 정보")
                headerInformation.setValue(JsonUtils.toPrettyJson(MDCUtils[MDCUtils.HEADER_MAP_MDC]))
                headerInformation.isShorten = false
                fields.add(headerInformation)
            }
            if (!ObjectUtils.isEmpty(MDCUtils[MDCUtils.PARAMETER_MAP_MDC])) {
                val bodyInformation = SlackField()
                bodyInformation.setTitle("Http Body 정보")
                bodyInformation.setValue(MDCUtils[MDCUtils.PARAMETER_MAP_MDC])
                bodyInformation.isShorten = false
                fields.add(bodyInformation)
            }
            slackAttachment.setFields(fields)

            val slackMessage = SlackMessage("")
            slackMessage.setChannel("#$channel")
            slackMessage.setUsername("${getBotName()} - ${getTitle()}")
            // slackMessage.setIcon(botEmoji)
            slackMessage.setIcon(getBotEmoji())
            slackMessage.setAttachments(listOf(slackAttachment))
            val slackApi = SlackApi(token)
            slackApi.call(slackMessage)
        }
    }

    /**
     * Gets host name.
     *
     * @return the host name
     */
    fun getHostName(): String? {
        try {
            return ContextUtil.getLocalHostName()
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * Gets stack trace.
     *
     * @param stackTraceElements the stack trace elements
     * @return the stack trace
     */
    fun getStackTrace(stackTraceElements: Array<StackTraceElementProxy>?): String? {
        if (stackTraceElements == null || stackTraceElements.isEmpty()) {
            return null
        }
        val sb = StringBuilder()
        for (element in stackTraceElements) {
            sb.append(element.toString())
            sb.append("\n")
        }
        return sb.toString()
    }


    /**
     * Is enabled boolean.
     *
     * @return the boolean
     */
    fun isEnabled(): Boolean {
        return enabled
    }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    fun getLevel(): Level? {
        return level
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    fun setLevel(level: Level?) {
        this.level = level
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    fun getToken(): String? {
        return token
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    fun setToken(token: String?) {
        this.token = token
    }

    /**
     * Gets channel.
     *
     * @return the channel
     */
    fun getChannel(): String? {
        return channel
    }

    /**
     * Sets channel.
     *
     * @param channel the channel
     */
    fun setChannel(channel: String?) {
        this.channel = channel
    }

    fun getTitle(): String? {
        return if(ObjectUtils.isEmpty(title)) "서비스 장애 감지" else title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getBotName(): String? {
        return if(ObjectUtils.isEmpty(botName)) "App" else botName
    }

    fun setBotName(botName: String?) {
        this.botName = botName
    }

    fun getBotEmoji(): String? {
        return if(ObjectUtils.isEmpty(botEmoji)) ":exclamation:" else botEmoji
    }

    fun setBotEmoji(botEmoji: String?) {
        this.botEmoji = botEmoji
    }

}