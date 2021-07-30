package me.practice.kotlinbatch.common.querydsl.support

import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.dsl.*
import me.practice.kotlinbatch.common.utils.DateUtils
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by LYT to 2021/07/29
 */
open class QuerydslCustomSupport {

    /**
     * dateTimePath <= date
     * @param dateTimePath DateTimePath<LocalDateTime>
     * @param date LocalDate
     * @return (com.querydsl.core.types.dsl.BooleanExpression..com.querydsl.core.types.dsl.BooleanExpression?)
     */
    protected fun isLoeDate(dateTimePath: DateTimePath<LocalDateTime>, date: LocalDate) =
        getFormattedDate(dateTimePath).loe(DateUtils.getFormattedDate(date))

    /**
     * LocalDateTime -> String Format Date
     * @param dateTimePath DateTimePath<LocalDateTime>
     * @return StringTemplate
     */
    protected fun getFormattedDate(dateTimePath: DateTimePath<LocalDateTime>): StringTemplate {
        return Expressions.stringTemplate(
            "DATE_FORMAT({0}, {1})",
            dateTimePath,
            ConstantImpl.create("%Y-%m-%d")
        )
    }

    /**
     * LocalDateTime -> String Format Date
     * @param dateTemplate DateTemplate<LocalDate>
     * @return StringTemplate
     */
    protected fun getFormattedDate(dateTemplate: DateTemplate<LocalDate>): StringTemplate {
        return Expressions.stringTemplate(
            "DATE_FORMAT({0}, {1})",
            dateTemplate,
            ConstantImpl.create("%Y-%m-%d")
        )
    }

    /**
     * SQL ADDDATE Function 사용
     * @param datePath DateTimePath<LocalDateTime>
     * @param numberPath NumberPath<Int>
     * @return DateTemplate<LocalDate>?
     */
    protected fun addDateTemplate(datePath: DateTimePath<LocalDateTime>, numberPath: NumberPath<Int>): DateTemplate<LocalDate> =
        Expressions.dateTemplate(
            LocalDate::class.java,
            "ADDDATE({0},{1})",
            datePath,
            numberPath
        )

}