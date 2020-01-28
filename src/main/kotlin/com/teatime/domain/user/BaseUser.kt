package com.teatime.domain.user

import com.fasterxml.jackson.annotation.JsonBackReference
import com.teatime.domain.accessory.Accessory
import com.teatime.domain.tea.Tea
import com.teatime.orm.AbstractJpaPersistable
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
class BaseUser(var username: String,
               var avatar: String?,
               val accountCreated: LocalDate,
               var description: String?,
               var emailAddress: String?) : AbstractJpaPersistable<BaseUser>(), Serializable {

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "author")
    val createdTeas: MutableSet<Tea> = mutableSetOf()

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "author")
    val accessories: MutableSet<Accessory> = mutableSetOf()

    fun addCreatedTea(tea: Tea) {
        if (!createdTeas.contains(tea)) {
            createdTeas.add(tea)

            tea.setTeaAuthor(this)
        }
    }

    fun removeTea(tea: Tea) {
        createdTeas.remove(tea)
    }
}
