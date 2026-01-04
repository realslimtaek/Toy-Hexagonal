package com.study.hexagonal.adapter.`in`.user.dto

import com.study.hexagonal.common.enums.Gender
import com.study.hexagonal.common.enums.Status

interface GetUsersCommand {
    val id: Long
    val name: String
    val age: Int
    val gender: Gender
    val status: Status
    val resigned: Boolean
    val adult: Boolean
}

data class GetUsersResponse(
    override val id: Long,
    override val name: String,
    override val age: Int,
    override val gender: Gender,
    override val status: Status,
    override val resigned: Boolean,
    override val adult: Boolean
): GetUsersCommand