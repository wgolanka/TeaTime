package com.teatime.domain.tea.config

import com.teatime.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.Entity

@Entity
class BrewingConfiguration(var brewingTime: String,
                           var ingredients: ArrayList<String>,
                           var drinkingTime: ArrayList<String>,
                           var isDifficultToMake: Boolean,
                           var description: String) : AbstractJpaPersistable<BrewingConfiguration>(), Serializable {
    override fun toString(): String {
        return "BrewingConfiguration(brewingTime='$brewingTime', ingredients=$ingredients, drinkingTime=$drinkingTime," +
                " isDifficultToMake=$isDifficultToMake, description='$description')"
    }
}
