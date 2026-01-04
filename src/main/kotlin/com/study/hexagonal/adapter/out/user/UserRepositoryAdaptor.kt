package com.study.hexagonal.adapter.out.user

import com.study.hexagonal.adapter.`in`.user.dto.SaveUserCommand
import com.study.hexagonal.application.port.out.user.GetUserPort
import com.study.hexagonal.application.port.out.user.SaveUserPort
import org.springframework.stereotype.Component


@Component
class UserRepositoryAdaptor(
    private val mapper: UserMapper,
    private val repository: UserRepository
): GetUserPort, SaveUserPort {

    override fun saveUser(req: SaveUserCommand) {
        mapper.toEntity(req).run(repository::save)
    }


}