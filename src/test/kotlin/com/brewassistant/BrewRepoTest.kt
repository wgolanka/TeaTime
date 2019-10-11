package com.brewassistant

import com.brewassistant.domain.tea.BrewRepository
import com.brewassistant.domain.user.PersonRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class BrewRepoTest(@Autowired val repository: BrewRepository,
                   @Autowired val personRepository: PersonRepository) {
    //todo make test connect to test db
    @Test
    fun `Get all brews by author id`() {
//        val person = Brewer("Sadie", "Adler", ByteArray(1), LocalDate.now(),
//                "sadie.adler@gmail.com", "556553729", mutableSetOf())

//        val brew = Tea("Green", "",
//                "", person, "", 0.0)
//
//        val hashCodeBefore = brew.hashCode()
//
//        repository.save(brew)
//        personRepository.save(person)
//
//        val hashCodeAfter = brew.hashCode()
//        assertThat(person.createdBrews).contains(brew)
//        assertThat(repository.findAll()).contains(brew)
//        assertThat(repository.getAllByAuthorIs(person)).contains(brew)
//        assertThat(hashCodeAfter).isEqualTo(hashCodeBefore)
    }
}