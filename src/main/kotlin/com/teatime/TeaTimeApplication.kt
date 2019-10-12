package com.teatime

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.teatime"])
class BrewFriendApplication

fun main(args: Array<String>) {
    runApplication<BrewFriendApplication>(*args)
}
