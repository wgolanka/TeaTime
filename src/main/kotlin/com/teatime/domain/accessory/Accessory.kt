package com.teatime.domain.accessory

import com.teatime.domain.tea.Tea
import com.teatime.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
class Accessory(var name: String,
                var priceRange: String,
                var description: String,
                var imageLink: String?,
                var isNecessary: Boolean) : Serializable, AbstractJpaPersistable<Accessory>() {

    @ManyToMany
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
        }
    }

    override fun toString(): String {
        return "Accessory(name='$name', priceRange='$priceRange', description='$description', " +
                ", isNecessary=$isNecessary, teas=$teas)"
    }
}