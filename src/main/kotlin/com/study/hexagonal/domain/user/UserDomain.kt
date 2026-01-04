package com.study.hexagonal.domain.user

import com.study.hexagonal.common.enums.Gender
import com.study.hexagonal.common.enums.Status
import java.time.LocalDateTime

/**
 * 사용자 도메인 모델 (Rich Domain Model)
 * - 데이터뿐만 아니라 도메인 로직(검증, 상태 변경 등)을 포함합니다.
 * - 불변 객체(Immutable Object)로 설계되어, 상태 변경 시 새로운 객체를 반환합니다.
 */
data class UserDomain(
    val id: Long,
    val name: String,
    val age: Int,
    val gender: Gender,
    val status: Status,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    init {
        require(name.isNotBlank()) { "이름은 비워둘 수 없습니다." }
        require(age >= 0) { "나이는 0보다 작을 수 없습니다." }
    }


    fun isAdult() = age >= 19
    fun isResigned() = status == Status.RESIGN

    // --- 비즈니스 행위 (상태 변경 로직) ---

    /**
     * 이름 변경
     * - 탈퇴한 회원은 이름을 변경할 수 없다는 도메인 규칙을 포함합니다.
     */
    fun rename(newName: String): UserDomain {
        require(newName.isNotBlank()) { "변경할 이름은 비워둘 수 없습니다." }
        
        if (isResigned()) {
            throw IllegalStateException("탈퇴한 회원은 이름을 변경할 수 없습니다.")
        }
        
        // 불변성을 유지하며 변경된 상태를 가진 새로운 객체 반환
        return this.copy(
            name = newName,
            updatedAt = LocalDateTime.now()
        )
    }

    fun resign(): UserDomain {
        if (isResigned()) {
            throw IllegalStateException("이미 탈퇴한 회원입니다.")
        }

        return this.copy(
            status = Status.RESIGN,
            updatedAt = LocalDateTime.now()
        )
    }
}
