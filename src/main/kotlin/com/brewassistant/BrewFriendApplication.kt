package com.brewassistant

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.brewassistant"])
class BrewFriendApplication

fun main(args: Array<String>) {
    runApplication<BrewFriendApplication>(*args)
}
