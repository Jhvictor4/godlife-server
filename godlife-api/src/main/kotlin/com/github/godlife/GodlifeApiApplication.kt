package com.github.godlife

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GodlifeApiApplication

fun main(args: Array<String>) {
    runApplication<GodlifeApiApplication>(*args)
}
