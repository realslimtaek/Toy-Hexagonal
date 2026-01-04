package com.study.hexagonal.common.enums.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

class BusinessValidationException(message: String) : RuntimeException(message)

data class ErrorResponse(
    val message: String
)

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessValidationException::class)
    fun handleBusinessException(e: BusinessValidationException): ResponseEntity<ErrorResponse> {

        val message = e.message ?: throw IllegalStateException("에러 메세지를 작성해주세요.")

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse(
                message = message
            )
        )
    }
}

