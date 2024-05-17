package com.example.task2_ftime

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val mHandler = Handler(Looper.getMainLooper()) //variable type: handler, initialize with a handler that operates in the main looper
    private var timeInSeconds: Long = 0
    private lateinit var fTimeText: TextView

    private var chronometer: Runnable = object : Runnable {
        override fun run() {
            timeInSeconds++
            updateTimer(timeInSeconds)
            mHandler.postDelayed(this, 100) // Use to schedule the runnable to run every 100 ms
        }

    } //Initialize with an object implementing their runnable interface

    private fun updateTimer(timeInSeconds: Long) {
        val hours = timeInSeconds/3600
        val minutes = (timeInSeconds%3600)/60
        val seconds = timeInSeconds%60

        val formattedTime = String.format("%02d:%02d:%02d",hours,minutes,seconds)
        fTimeText.text = formattedTime
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        fTimeText = findViewById(R.id.foregroundTimer)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        //Toast.makeText(this, "Inside onResume", Toast.LENGTH_SHORT).show()
        mHandler.post(chronometer)
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "Inside onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        //Toast.makeText(this, "Inside onResume", Toast.LENGTH_SHORT).show()
        mHandler.removeCallbacks(chronometer)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}