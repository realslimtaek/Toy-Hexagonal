package com.study.hexagonal.adapter.out.user

import com.study.hexagonal.adapter.`in`.user.dto.SaveUserCommand
import com.study.hexagonal.application.port.out.user.GetUserPort
import com.study.hexagonal.application.port.out.user.SaveUserPort
import com.study.hexagonal.common.enums.Status
import com.study.hexagonal.common.enums.exception.BusinessValidationException
import com.study.hexagonal.domain.user.UserDomain
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull


@Component
class UserRepositoryAdaptor(
    private val mapper: UserMapper,
    private val repository: UserRepository
) : GetUserPort, SaveUserPort {

    override fun saveUser(req: SaveUserCommand) {
        mapper.toEntity(req).run(repository::save)
    }

    override fun save(domain: UserDomain) {
        mapper.toEntity(domain).run(repository::save)
    }

    override fun getUsers(status: Status): List<UserDomain> =
        repository.findAllByStatusIs(status).map(mapper::toDomain)

    override fun getUser(id: Long): UserDomain =
        repository.findById(id).getOrNull()?.run(mapper::toDomain)
            ?: throw BusinessValidationException("존재하지 않는 회원입니다")

}