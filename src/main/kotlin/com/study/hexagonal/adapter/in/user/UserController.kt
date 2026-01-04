package com.study.hexagonal.adapter.`in`.user

import com.study.hexagonal.adapter.`in`.user.dto.GetUsersCommand
import com.study.hexagonal.adapter.`in`.user.dto.SaveUserRequest
import com.study.hexagonal.application.port.`in`.user.GetUserUseCase
import com.study.hexagonal.application.port.`in`.user.SaveUserUseCase
import com.study.hexagonal.common.enums.Status
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveUser(
        @RequestBody body: SaveUserRequest
    ) {
        saveUserUseCase.saveUser(body)
    }

    @GetMapping
    fun getUsers(
        @RequestParam status: Status
    ): ResponseEntity<List<GetUsersCommand>> =
        getUserUseCase.getUsers(status).run {
            ResponseEntity.ok(this)
        }
}