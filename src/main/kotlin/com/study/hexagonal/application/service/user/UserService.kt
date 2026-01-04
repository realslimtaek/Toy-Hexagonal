package com.study.hexagonal.application.service.user

import com.study.hexagonal.adapter.`in`.user.dto.SaveUserCommand
import com.study.hexagonal.application.port.`in`.user.GetUserUseCase
import com.study.hexagonal.application.port.`in`.user.SaveUserUseCase
import com.study.hexagonal.application.port.out.user.GetUserPort
import com.study.hexagonal.application.port.out.user.SaveUserPort
import org.springframework.stereotype.Service


@Service
class UserService(
    private val getUserPort: GetUserPort,
    private val saveUserPort: SaveUserPort
): GetUserUseCase, SaveUserUseCase {

    override fun saveUser(req: SaveUserCommand) {
        saveUserPort.saveUser(req)
    }
}