package com.study.hexagonal.adapter.out.user

import com.study.hexagonal.adapter.`in`.user.dto.SaveUserCommand
import com.study.hexagonal.common.enums.Status
import org.springframework.stereotype.Component

@Component
object UserMapper {

    fun toEntity(req: SaveUserCommand): UserEntity =
        UserEntity(
            id = null,
            name = req.name,
            age = req.age,
            gender = req.gender,
            status = Status.ACTIVE
        )
}
