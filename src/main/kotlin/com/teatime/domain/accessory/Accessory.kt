package com.teatime.domain.accessory

import com.teatime.domain.tea.Tea
import com.teatime.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
class Accessory(private var name: String,
                private var priceRange: String,
                private var description: String,
                private var imageLink: String?,
                private var isNecessary: Boolean) : Serializable, AbstractJpaPersistable<Accessory>() {

    @ManyToMany
    private var brews: MutableSet<Tea> = mutableSetOf()

    fun addTea(tea: Tea) {
        if (!brews.contains(tea)) {
            brews.add(tea)
            tea.addAccessory(this)
        }
    }

    fun removeTea(tea: Tea) {
        if (brews.contains(tea)) {
            brews.remove(tea)
            tea.removeAccessory(this)
        }
    }

    override fun toString(): String {
        return "Accessory(name='$name', priceRange='$priceRange', description='$description', " +
                ", isNecessary=$isNecessary, brews=$brews)"
    }
}