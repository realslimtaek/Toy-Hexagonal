package com.study.hexagonal.adapter.`in`.user

import com.study.hexagonal.application.port.`in`.user.GetUserUseCase
import com.study.hexagonal.application.port.`in`.user.SaveUserUseCase
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
)