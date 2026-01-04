package com.study.hexagonal.domain.user

import com.study.hexagonal.common.enums.Gender
import com.study.hexagonal.common.enums.Status
import java.time.LocalDateTime

data class UserDomain(
    val id: Long,
    val name: String,
    val age: Int,
    val gender: Gender,
    val status: Status,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    fun isAdult() = age >= 19
    fun isResigned() = status == Status.RESIGN
}