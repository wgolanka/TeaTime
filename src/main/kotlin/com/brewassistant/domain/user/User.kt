package com.brewassistant.domain.user

import com.brewassistant.domain.tea.Tea
import com.brewassistant.orm.AbstractJpaPersistable
import com.fasterxml.jackson.annotation.JsonBackReference
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
class User(var nickname: String,
           var avatar: ByteArray?,
           val accountCreated: LocalDate,
           val description: String,
           var emailAddress: String?) : AbstractJpaPersistable<User>(), Serializable {

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "author")
    val createdTeas: MutableSet<Tea> = mutableSetOf()

    fun addCreatedBrew(tea: Tea) {
        if (!createdTeas.contains(tea)) {
            createdTeas.add(tea)

            tea.setBrewAuthor(this)
        }
    }

    fun removeBrew(tea: Tea) {
        createdTeas.remove(tea)
    }
}
