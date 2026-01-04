package com.study.hexagonal.application.port.`in`.user

import com.study.hexagonal.adapter.`in`.user.dto.RenameUserCommand
import com.study.hexagonal.adapter.`in`.user.dto.SaveUserCommand

interface SaveUserUseCase {

    fun saveUser(req: SaveUserCommand)

    fun renameUser(req: RenameUserCommand)

    fun resignUser(id: Long)
}