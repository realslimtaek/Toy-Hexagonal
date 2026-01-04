package com.study.hexagonal.application.port.out.user

import com.study.hexagonal.common.enums.Status
import com.study.hexagonal.domain.user.UserDomain

interface GetUserPort {

    fun getUsers(status: Status): List<UserDomain>
}