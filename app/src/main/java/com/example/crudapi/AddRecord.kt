package com.example.crudapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.crudapi.R

class AddRecord : BaseActivity() {

    lateinit var edtEditName : EditText
    lateinit var edtEditDescription : EditText
    lateinit var edtEditPrice : EditText
    lateinit var edtEditRating : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_record)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtEditName = findViewById(R.id.edtEditName)
        edtEditDescription = findViewById(R.id.edtEditDescription)
        edtEditPrice = findViewById(R.id.edtEditPrice)
        edtEditRating = findViewById(R.id.edtEditRating)

    }

    fun addNewRecordOnClick(v: View) {
//        val name = edtEditName.text.toString()
//        val description = edtEditDescription.text.toString()
//        val price = edtEditPrice.text.toString().toDoubleOrNull() ?: 0.0
//        val rating = edtEditRating.text.toString().toIntOrNull()
//
//        if (rating == null || rating < 1 || rating > 5) {
//            toastIt("Rating must be a number between 1 and 5")
//            return
//        }
//
//        val recordItem = RecordsItem(
//                            ++idCounter,
//                            name,
//                            description,
//                            price,
//                            rating)
//        recordsList.add(recordItem)

        //Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)

        //Write the edited record to the online database:
        val request: StringRequest = object : StringRequest(
            Method.POST,
            "$baseUrl",
            Response.Listener { response ->
                Log.i("CRUDapi", response)
            },
            Response.ErrorListener {
                Log.i("CRUDapi", "it no worky - ${it.message}")
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["name"] = edtEditName.text.toString()
                params["description"] = edtEditDescription.text.toString()
                params["price"] = edtEditPrice.text.toString()
                params["rating"] = edtEditRating.text.toString()
                return params
            }
        }

        request.setShouldCache(false)
        queue.add(request)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun showAllRecordsOnClick(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}