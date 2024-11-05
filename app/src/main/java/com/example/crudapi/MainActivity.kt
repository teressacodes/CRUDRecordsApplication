package com.example.crudapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.crudapi.R
import kotlin.random.Random

class MainActivity : BaseActivity() {
    lateinit var recyclerView : RecyclerView
    private lateinit var recordsListAdapter : RecordsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recordsRecycler)
        recordsListAdapter = RecordsAdapter(recordsList) { position ->

                val intent = Intent(this, ShowRecord::class.java)
                currentRecord = position
                startActivity(intent)

        }

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = recordsListAdapter

        recordsListAdapter.notifyDataSetChanged()

        //Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)

        //Request a string response from the provided URL
        val stringRequest = JsonArrayRequest(
            Request.Method.GET, baseUrl,
            null, //jsonrequestobject
            { response ->
                for(i in 0 until response.length()) {
                    val item = response.getJSONObject(i)
                    val recordItem = RecordsItem(
                        item.getInt("id"),
                        item.getString("name"),
                        item.getString("description"),
                        item.getDouble("price"),
                        item.getInt("rating")
                    )
                    recordsList.add(recordItem)
                }

                recordsListAdapter.notifyDataSetChanged()
                //Display the first 500 characters of the response string
                Log.i("CRUDapi", "Response is: ${response.toString()}")
            },
            Response.ErrorListener {
                Log.i("CRUDapi", "it no worky - ${it.message}")
            })

        //Add request to the RequestQueue
        stringRequest.setShouldCache(false)
        queue.add(stringRequest)
    }

//    fun addMockDataOnClick(v: View) {
//        val randomWords = arrayOf(
//            "lorem",
//            "ipsum",
//            "dolor",
//            "sit",
//            "amet",
//            "consectetur",
//            "adipiscing",
//            "elit",
//            "sed",
//            "do",
//            "eiusmod",
//            "tempor",
//            "incididunt",
//            "ut",
//            "labore",
//            "et",
//            "dolore",
//            "magna",
//            "aliqua"
//        )
//
//        if (recordsList.isEmpty()) {
//            readRecordsOnFile()
//        }
//
//        val currentID = idCounter
//        for (i in 1..41) {
//            val productName = "Product $i"
//            val descriptionWords =
//                List(3) { randomWords.random() }.joinToString(" ")
//            val price = String.format("%.2f", Random.nextDouble(1.0, 101.0)).toDouble()
//            val recordItem = RecordsItem(
//                currentID + i,
//                productName,
//                descriptionWords,
//                price,
//                Random.nextInt(1, 6)
//            )
//            recordsList.add(recordItem)
//        }
//        idCounter += 41
//        writeRecordsToFile()
//        recordsListAdapter.notifyDataSetChanged()
//    }

    fun addRecordOnClick(v: View) {
        val intent = Intent(this, AddRecord::class.java)
        startActivity(intent)
    }

    fun clearMockDataOnClick(v: View) {
        recordsList.clear()
        recordsListAdapter.notifyDataSetChanged()
    }
}