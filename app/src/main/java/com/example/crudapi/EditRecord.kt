package com.example.crudapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.crudapi.R

class EditRecord : BaseActivity() {

    lateinit var edtEditName : EditText
    lateinit var edtEditDescription : EditText
    lateinit var edtEditPrice : EditText
    lateinit var edtEditRating : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_record)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtEditName = findViewById(R.id.edtEditName)
        edtEditDescription = findViewById(R.id.edtEditDescription)
        edtEditPrice = findViewById(R.id.edtEditPrice)
        edtEditRating = findViewById(R.id.edtEditRating)

        var record = recordsList [currentRecord]

        edtEditName.setText(record.name)
        edtEditDescription.setText(record.description)
        edtEditPrice.setText(record.price.toString())
        edtEditRating.setText(record.rating.toString())

    }

    fun saveChangesOnClick(v: View) {
        var record = recordsList[currentRecord]

        record.name = edtEditName.text.toString()
        record.description = edtEditDescription.text.toString()
        record.price = edtEditPrice.text.toString().toDouble()
        record.rating = edtEditRating.text.toString().toInt()

        if (record.rating == null || record.rating < 1 || record.rating > 5) {
            toastIt("Rating must be a number between 1 and 5")
            return
        }

        record.modifiedAt = RecordsItem.getCurrentDateTime()
        recordsList[currentRecord] = record

        //Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)

        //Write the edited record to the online database:
        val request: StringRequest = object : StringRequest(
            Method.PUT,
            "$baseUrl/${record.id}",
            Response.Listener { response ->
                Log.i("CRUDapi", response)
            },
            Response.ErrorListener {
                Log.i("CRUDapi", "it no worky - ${it.message}")
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["name"] = record.name
                params["description"] = record.description
                params["price"] = record.price.toString()
                params["rating"] = record.rating.toString()
                params["modifiedAt"] = record.modifiedAt
                return params

            }
        }

        request.setShouldCache(false)
        queue.add(request)

        val intent = Intent(this, ShowRecord::class.java)
        startActivity(intent)
    }

    fun showAllRecordsOnClick(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}