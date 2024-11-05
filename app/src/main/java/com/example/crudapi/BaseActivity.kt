package com.example.crudapi

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

var recordsList = ArrayList<RecordsItem>()
var currentRecord = 0
val baseUrl = "https://productsapi.teressaellison.com/api/products"


open class BaseActivity() : AppCompatActivity() {

    open fun toastIt(msg: String?) {
        Toast.makeText(
            applicationContext,
            msg, Toast.LENGTH_SHORT
        ).show()
    }
}