package com.ugmobi.assignment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var  progressbar : ProgressBar
    private var videos: ArrayList<Videos> = ArrayList()
    private var datalist : ArrayList<Videos> = ArrayList()
    private var titlelist : ArrayList<String> = ArrayList()
    private lateinit var adapter : Adapter
    private lateinit var searbar : EditText
    private lateinit var recyclerview: RecyclerView
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            try {
                val client = getClient()
                val supabaseResponse = client.from("Videos").select().decodeList<Videos>()
                videos.addAll(supabaseResponse)
                for (videos in videos){
                    val data = Videos(videos.id,videos.ch_name,videos.title,
                        videos.description,videos.likes,videos.thumbnail,videos.videourl)
                    datalist.add(data)
                }
            } catch (e: Exception) {
                // Handle exceptions here
                Log.e("MainActivity", "Error fetching videos: ${e.message}")
            }
        }

        searbar = findViewById(R.id.searchbar)
        progressbar = findViewById(R.id.progressbar)

        searbar.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

        })

        val handler:Handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(Runnable {
            adapter = Adapter(this,datalist)
            recyclerview = findViewById(R.id.recyclerview)
            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = adapter
            for (item in datalist){
                titlelist.add(item.title)
            }
            progressbar.visibility = View.GONE
        },4000)
    }

    private fun getClient():SupabaseClient{
        return createSupabaseClient(
            supabaseUrl = "https://kkltcdqhcugcjousafnp.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImtrbHRjZHFoY3VnY2pvdXNhZm5wIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTUwMDEzMTEsImV4cCI6MjAzMDU3NzMxMX0.czohxFvCemZdZ4EZhW7zCPHcGIOMSl2LaxcEQJzD6u0"
        ) {
            install(Postgrest)
            //install other modules
        }
    }
    private fun filter(text: String) {
        val filteredList = ArrayList<Videos>()
        for (item in datalist) {
            if (item.title.contains(text, ignoreCase = true)) {
                filteredList.add(item)
            }
        }
        adapter.filterList(filteredList)
    }
}