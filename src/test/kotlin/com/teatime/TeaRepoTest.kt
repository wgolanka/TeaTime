package com.teatime

import com.teatime.domain.tea.TeaRepository
import com.teatime.domain.user.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class TeaRepoTest(@Autowired val repository: TeaRepository,
                  @Autowired val userRepository: UserRepository) {
    //todo make test connect to test db
    @Test
    fun `Get all brews by author id`() {
//        val person = BaseUser("SadieAdler", ByteArray(1), LocalDate.now(), "I'm Sadie!",
//                "sadie.adler@gmail.com")

//        val brew = Tea("Green", "",
//                "", person, "", 0.0)
//
//        val hashCodeBefore = brew.hashCode()
//
//        repository.save(brew)
//        userRepository.save(person)
//
//        val hashCodeAfter = brew.hashCode()
//        assertThat(person.createdBrews).contains(brew)
//        assertThat(repository.findAll()).contains(brew)
//        assertThat(repository.getAllByAuthorIs(person)).contains(brew)
//        assertThat(hashCodeAfter).isEqualTo(hashCodeBefore)
    }
}