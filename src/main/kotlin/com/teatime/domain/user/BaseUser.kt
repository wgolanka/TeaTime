package com.teatime.domain.user

import com.fasterxml.jackson.annotation.JsonBackReference
import com.teatime.domain.tea.Tea
import com.teatime.orm.AbstractJpaPersistable
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
class BaseUser(var nickname: String,
               var avatar: ByteArray?,
               val accountCreated: LocalDate,
               val description: String?,
               var emailAddress: String?) : AbstractJpaPersistable<BaseUser>(), Serializable {

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "author")
    val createdTeas: MutableSet<Tea> = mutableSetOf()

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
