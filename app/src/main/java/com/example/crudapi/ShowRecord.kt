package com.example.crudapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudapi.R

class ShowRecord : BaseActivity() {
    lateinit var record : RecordsItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_record)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtName : TextView = findViewById(R.id.txtName)
        val txtDescription : TextView = findViewById(R.id.txtDescription)
        val txtPrice : TextView = findViewById(R.id.txtPrice)
        val txtRating : TextView = findViewById(R.id.txtRating)

        record = recordsList [currentRecord]
        txtName.text = record.name
        txtDescription.text = record.description
        txtPrice.text = record.price.toString()
        txtRating.text = record.rating.toString()
    }

    fun editRecordOnClick(v: View) {
        val intent = Intent(this, EditRecord::class.java)
        startActivity(intent)
    }

    fun showAllRecordsOnClick(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun deleteRecordOnClick(v: View) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this record?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, which ->
                toastIt("You want to delete the record")
                recordsList.removeAt(currentRecord)

                val queue = Volley.newRequestQueue(this)

                //Request a string response from the provided URL
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.DELETE, "$baseUrl/${record.id}",
                    null, //jsonRequestObject
                    { response ->
                        //Display the first 500 characters of the response string
                        Log.i("CRUDapi", "Response is: ${response.toString()}")
                    },
                    Response.ErrorListener {
                        Log.i("CRUDapi", "it no worky - ${it.message}")
                    })

                //Add request to the RequestQueue
                jsonObjectRequest.setShouldCache(false)
                queue.add(jsonObjectRequest)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("No") { dialog, which ->
                toastIt("You canceled")
                dialog.cancel()
            }
            .create()
            .show()
    }
}