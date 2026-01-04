package com.study.hexagonal.adapter.out.user

import com.study.hexagonal.adapter.`in`.user.dto.SaveUserCommand
import com.study.hexagonal.common.enums.Status
import com.study.hexagonal.domain.user.UserDomain
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

    fun toEntity(domain: UserDomain): UserEntity =
        UserEntity(
            id = domain.id,
            name = domain.name,
            age = domain.age,
            gender = domain.gender,
            status = domain.status
        )

    fun toDomain(entity: UserEntity): UserDomain = UserDomain(
        id = entity.id!!,
        name = entity.name,
        age = entity.age,
        gender = entity.gender,
        status = entity.status,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt
    )

}
