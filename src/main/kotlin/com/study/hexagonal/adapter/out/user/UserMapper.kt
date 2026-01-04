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

    fun toDomain(entities: List<UserEntity>): List<UserDomain> =
        entities.map {
            UserDomain(
                id = it.id!!,
                name = it.name,
                age = it.age,
                gender = it.gender,
                status = it.status,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }
}
