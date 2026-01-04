package com.study.hexagonal.application.port.out.user

import com.study.hexagonal.adapter.`in`.user.dto.SaveUserCommand
import com.study.hexagonal.domain.user.UserDomain

interface SaveUserPort {

    fun saveUser(req: SaveUserCommand)

    fun save(domain: UserDomain)
}