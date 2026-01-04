package com.study.hexagonal.application.port.`in`.user

import com.study.hexagonal.adapter.`in`.user.dto.GetUsersCommand
import com.study.hexagonal.common.enums.Status

interface GetUserUseCase {

    fun getUsers(status: Status): List<GetUsersCommand>
}