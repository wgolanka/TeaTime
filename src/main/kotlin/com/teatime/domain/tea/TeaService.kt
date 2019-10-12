package com.teatime.domain.tea

import com.teatime.domain.tea.config.BrewingConfigRepository
import com.teatime.domain.tea.config.BrewingConfiguration
import com.teatime.domain.user.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TeaService(private val userService: UserService,
                 private val teaRepository: TeaRepository,
                 private val brewingConfigRepository: BrewingConfigRepository) {

    fun saveNewTea(tea: Tea, brewingConfig: BrewingConfiguration) {
        brewingConfigRepository.saveAndFlush(brewingConfig)

        val user = userService.getCurrentUser()
        teaRepository.saveAndFlush(
                Tea(
                        tea.name,
                        LocalDate.now(),
                        tea.imageLink,
                        tea.originCountry,
                        tea.caffeineContent,
                        tea.harvestSeasons,
                        user,
                        brewingConfig
                )
        )
    }

    fun getAll(): List<Tea> {
        return teaRepository.getAllByIdIsNotNull()
    }
}