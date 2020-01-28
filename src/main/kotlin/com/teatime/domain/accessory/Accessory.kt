package com.teatime.domain.accessory

import com.fasterxml.jackson.annotation.JsonBackReference
import com.teatime.domain.tea.Tea
import com.teatime.domain.user.BaseUser
import com.teatime.orm.AbstractJpaPersistable
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
class Accessory(var name: String,
                var priceFrom: Double,
                var priceTo: Double,
                var priceRange: String,
                var description: String,
                var imageLink: String?,
                var necessary: Boolean,
                val createdDate: LocalDate,

                @ManyToOne //  @JoinColumn(name = "user_id", nullable = false)
                var author: BaseUser) : Serializable, AbstractJpaPersistable<Accessory>() {

    @JsonBackReference
    @ManyToMany(mappedBy = "accessories")
    var teas: MutableSet<Tea> = mutableSetOf()

    fun addTea(tea: Tea) {
        if (!teas.contains(tea)) {
            teas.add(tea)
            tea.addAccessory(this)
        }
    }

    fun removeTea(tea: Tea) {
        if (teas.contains(tea)) {
            teas.remove(tea)
            tea.removeAccessory(this)
            return
        }
    }

    fun contains(tea: Tea): Boolean {
        return teas.stream().anyMatch { it.getId()!! == tea.getId() }
    }

    override fun toString(): String {
        return "Accessory(name='$name', priceRange='$priceRange', description='$description', " +
                ", isNecessary=$necessary, teas=${teas.size})"
    }
}