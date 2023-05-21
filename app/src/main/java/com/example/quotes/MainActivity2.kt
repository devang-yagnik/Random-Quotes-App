package com.example.quotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.quote.MySingleton

class MainActivity2 : AppCompatActivity() {

    val url = "https://zenquotes.io/api/random"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        load()
    }
    fun load(){
        findViewById<TextView>(R.id.quote).text = ""
        findViewById<TextView>(R.id.author).text = ""
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
                findViewById<TextView>(R.id.quote).text = "\""+response.getJSONObject(0).getString("q")+"\""
                findViewById<TextView>(R.id.author).text = "-"+response.getJSONObject(0).getString("a")
            },
            { error ->
                // TODO: Handle error
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
                Log.e("error", error.toString())
            }
        )

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
    }
    fun share(view: View) {
        val string = findViewById<TextView>(R.id.quote).text.toString() + "\n" + findViewById<TextView>(R.id.author).text.toString()
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, string)
        val chooser = Intent.createChooser(intent, "Share Using...")
        startActivity(chooser)
    }
    fun next(view: View) {
        load()
    }
}