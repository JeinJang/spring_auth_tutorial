package com.example.spring_auth_tutorial.common.exception

class InvalidInputException(
    val fieldName: String = "",
    message: String = "Invalid Input"
): RuntimeException(message)