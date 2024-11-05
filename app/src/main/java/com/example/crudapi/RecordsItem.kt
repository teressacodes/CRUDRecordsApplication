package com.example.crudapi

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecordsItem(var id: Int,
                  var name: String,
                  var description: String,
                  var price: Double,
                  var rating: Int,
                  var createdAt: String = getCurrentDateTime(),
                  var modifiedAt: String = createdAt
) {
    companion object {
         fun getCurrentDateTime(): String {
            val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
            return dateFormat.format(Date())
        }
    }

    fun toCSV(): String {
        return "$id,$name,$description,$price,$rating,$createdAt,$modifiedAt"
    }

    override fun toString(): String {
        return "$id : $name : $description : $price : $rating : $createdAt : $modifiedAt"
    }
}
