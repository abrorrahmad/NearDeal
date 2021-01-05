package com.abrorrahmad.neardeal.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Cart : RealmObject(){

    @PrimaryKey
    var id: Int = 0
    var productId: String? = null
    var productName: String? = null
    var photo: String? = null
    var price: Double? = 0.toDouble()
    var lat: Double? = 0.toDouble()
    var lng: Double? = 0.toDouble()
}