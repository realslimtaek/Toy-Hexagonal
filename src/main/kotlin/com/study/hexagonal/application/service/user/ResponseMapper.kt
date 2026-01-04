package com.study.hexagonal.application.service.user

import com.study.hexagonal.adapter.`in`.user.dto.GetUsersResponse
import com.study.hexagonal.domain.user.UserDomain
import org.springframework.stereotype.Component

@Component
object ResponseMapper {

    fun toResponse(domains: List<UserDomain>) = domains.map {
        GetUsersResponse(
            id = it.id,
            name = it.name,
            age = it.age,
            gender = it.gender,
            status = it.status,
            resigned = it.isResigned(),
            adult = it.isAdult()
        )
    }
}