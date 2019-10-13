package com.teatime.domain.tea

import com.teatime.domain.accessory.Accessory
import com.teatime.domain.tea.config.BrewingConfiguration
import com.teatime.domain.user.BaseUser
import com.teatime.orm.AbstractJpaPersistable
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
          var author: BaseUser,

          @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
          var brewingConfig: BrewingConfiguration?) : AbstractJpaPersistable<Tea>(), Serializable {

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    var accessories = mutableSetOf<Accessory>()

    private val maxAccessories = 5

    init {
        author.addCreatedTea(this)
    }

    fun setTeaAuthor(baseUser: BaseUser) {
        if (author == baseUser) {
            return
        }

        author.removeTea(this)

        author = baseUser
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
        return "Tea(name='$name', created=$created, imageLink=$imageLink, originCountry='$originCountry'," +
                " caffeineContent=$caffeineContent, harvestSeasons=$harvestSeasons, author=$author, " +
                "brewingConfig=$brewingConfig, accessories=${accessories.size}, maxAccessories=$maxAccessories)"
    }


}