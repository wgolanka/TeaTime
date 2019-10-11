package com.brewassistant.domain.tea

import com.brewassistant.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.Entity

@Entity
class TeaConfiguration(var brewingTime: String,
                       var ingredients: ArrayList<String>,
                       var drinkingTime: ArrayList<String>,
                       var isDifficultToMake: Boolean,
                       val description: String) : AbstractJpaPersistable<TeaConfiguration>(), Serializable
