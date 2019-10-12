package com.brewassistant.domain.tea

import com.brewassistant.domain.accessory.Accessory
import com.brewassistant.domain.user.User
import com.brewassistant.orm.AbstractJpaPersistable
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

@Entity
class Tea(var name: String,
          var created: LocalDate,
          var imageLink: String?,
          var originCountry: String,
          var caffeineContent: Double,
          var harvestSeasons: ArrayList<String>,

          @ManyToOne
          @JoinColumn(name = "user_id", nullable = false)
          var author: User,

          @OneToOne
          val brewingConfig: BrewingConfiguration) : AbstractJpaPersistable<Tea>(), Serializable {

    @ManyToMany(fetch = FetchType.EAGER)
    val accessories = mutableSetOf<Accessory>()

    private val maxAccessories = 5

    init {
        author.addCreatedTea(this)
    }

    fun setTeaAuthor(user: User) {
        if (author == user) {
            return
        }
        //author is never nullable
        author.removeTea(this)

        author = user
        author.addCreatedTea(this)
    }

    fun addAccessory(accessory: Accessory) {
        if (!accessories.contains(accessory) && accessories.size < maxAccessories) {
            accessories.add(accessory)

            accessory.addTea(this)
        }
    }

    fun removeAccessory(accessory: Accessory) {
        if (accessories.contains(accessory)) {
            accessories.remove(accessory)
            accessory.removeTea(this)
        }
    }

    override fun toString(): String {
        return "Tea(name='$name', created=$created, imageLink=$imageLink, originCountry='$originCountry', " +
                "caffeineContent=$caffeineContent, harvestSeasons=$harvestSeasons, author=${author.nickname}, " +
                "teaConfig=$brewingConfig, accessories=$accessories, maxAccessories=$maxAccessories)"
    }

}