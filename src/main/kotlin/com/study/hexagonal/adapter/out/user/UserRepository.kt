package com.study.hexagonal.adapter.out.user

import com.study.hexagonal.common.enums.Status
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long> {

    fun findAllByStatusIs(status: Status?): List<UserEntity>
}