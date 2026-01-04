package com.study.hexagonal.application.port.out.user

import com.study.hexagonal.adapter.`in`.user.dto.SaveUserCommand

interface SaveUserPort {

    fun saveUser(req: SaveUserCommand)
}