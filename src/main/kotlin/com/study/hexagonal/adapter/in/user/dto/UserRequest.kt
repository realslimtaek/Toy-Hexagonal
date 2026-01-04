package com.study.hexagonal.adapter.`in`.user.dto

import com.study.hexagonal.common.enums.Gender
import com.study.hexagonal.common.enums.exception.BusinessValidationException

interface SaveUserCommand {

    val name: String
    val age: Int
    val gender: Gender
}

class SaveUserRequest(
    override val name: String,
    override val age: Int,
    override val gender: Gender
) : SaveUserCommand {
    init {
        require(this.age > 7) { throw BusinessValidationException("7세 이하는 가입할 수 없습니다.") }
        require(this.name.length <= 5) { throw BusinessValidationException("이름은 5자 이하여야 합니다.") }
    }
}