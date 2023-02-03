package com.example.exercise3recyclerviewcustomadapter

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var tts: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                tts.language = Locale.US
            } else {
                // Error initializing TextToSpeech
                Log.e("MainActivity", "Error initializing TextToSpeech")
            }
        })

        recyclerView = findViewById(R.id.mRecyclerView)
        val data = (0..1000).toList()
        recyclerView.adapter = MyAdapter(data,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
    //destroy the item when speaking stops
    override fun onDestroy() {
        super.onDestroy()
        if (tts.isSpeaking) {
            tts.stop()
        }
        tts.shutdown()
    }

    //call the speak function in MyAdapter in fun onBindViewHolder
    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
