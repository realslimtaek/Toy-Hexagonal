package com.study.hexagonal.adapter.out.user

import jakarta.persistence.*
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.time.ZoneId

@Entity
@Table(name = "USER")
class UserEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "ID",
        nullable = false,
        columnDefinition = "bigint(11)"
    )
    var id: Long,

    @Column(name = "NAME", nullable = false, columnDefinition = "varchar(5)", comment = "사용자 명")
    var name: String,

    @Column(name = "AGE", nullable = false, columnDefinition = "int(2)", comment = "나이")
    var age: Int,

    @Column(name = "GENDER", nullable = false, columnDefinition = "varchar(1)", comment = "성별")
    var gender: String,

    @Column(name = "STATUS", nullable = false, columnDefinition = "ENUMERATED")
    @Enumerated(EnumType.STRING)
    var status: UserStatus,

    @CreatedDate
    @Column(
        name = "CREATED_AT",
        nullable = false,
        updatable = false,
        columnDefinition = "datetime",
        comment = "생성일"
    ) var createdAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")),

    @UpdateTimestamp
    @Column(
        name = "UPDATED_AT",
        nullable = false,
        columnDefinition = "datetime",
        comment = "수정일"
    ) var updatedAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")),

)

enum class UserStatus {
    ACTIVE,
    RESIGN
}