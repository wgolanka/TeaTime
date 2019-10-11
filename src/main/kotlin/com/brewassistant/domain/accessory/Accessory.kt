package com.brewassistant.domain.accessory

import com.brewassistant.domain.tea.Tea
import com.brewassistant.orm.AbstractJpaPersistable
import java.io.Serializable
import java.util.*
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
                "image=${Arrays.toString(image)}, isNecessary=$isNecessary, brews=$brews)"
    }
}