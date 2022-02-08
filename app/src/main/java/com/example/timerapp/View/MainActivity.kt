package com.example.timerapp.View

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.timerapp.R
import com.example.timerapp.ViewModel.TimerViewModel

import java.util.*
class MainActivity : AppCompatActivity() {
    var timerText: TextView? = null
    var stopStartButton: Button? = null
    private lateinit var increment: Button
    private lateinit var Decrement : Button
    var timer: Timer? = null
    var timerTask: TimerTask? = null
    var timerStarted = false


    lateinit var timeViewModel: TimerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timerText = findViewById<View>(R.id.timerText) as TextView
        stopStartButton = findViewById<View>(R.id.startStopButton) as Button
        increment = findViewById(R.id.btnIncrement)
        Decrement = findViewById(R.id.btnDecrement)
        timer = Timer()
        timeViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
        //this will increment time value by 2
         increment.setOnClickListener {
             runOnUiThread { timeViewModel.countIncrement = 2 }

         }
        //this will decrease time value by 2
        Decrement.setOnClickListener(View.OnClickListener { runOnUiThread { timeViewModel.countIncrement = -2 } })
    }

    fun resetTapped(view: View?) {
        val resetAlert = AlertDialog.Builder(this)
        resetAlert.setTitle("Reset Timer")
        resetAlert.setMessage("Are you sure you want to reset the timer?")
        resetAlert.setPositiveButton(
            "Reset"
        ) { dialogInterface, i ->
            if (timerTask != null) {
                timerTask!!.cancel()
                timeViewModel.time = 0.0
                timerStarted = false
                timerText!!.text = formatTime(0, 0, 0)
            }
        }
        resetAlert.setNeutralButton(
            "Cancel"
        ) { dialogInterface, i ->
            //do nothing
        }
        resetAlert.show()
    }

    fun startStopTapped(view: View?) {
        if (timerStarted == false) {
            timerStarted = true
            setButtonUI("STOP", R.color.black)
            startTimer()
        } else {
            timerStarted = false
            setButtonUI("START", R.color.white)
            timerTask!!.cancel()
        }
    }

    private fun setButtonUI(start: String, color: Int) {
        stopStartButton!!.text = start
        stopStartButton!!.setTextColor(ContextCompat.getColor(this, color))
    }

    private fun startTimer() {
        timerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                      timeViewModel.time = timeViewModel.time+timeViewModel.countIncrement
                      timeViewModel.incrementValue = getTimerText()
                      timerText?.text = timeViewModel.incrementValue
                }
            }
        }
        timer!!.scheduleAtFixedRate(timerTask, 0, 1000)
    }


    private fun getTimerText(): String {
        val rounded = Math.round(timeViewModel.time).toInt()
        timeViewModel.second = rounded % 86400 % 3600 % 60
        timeViewModel.minutes = rounded % 86400 % 3600 / 60
        timeViewModel.hours = rounded % 86400 / 3600
        return formatTime(timeViewModel.second, timeViewModel.minutes, timeViewModel.hours)
    }

    private fun formatTime(seconds: Int, minutes: Int, hours: Int): String  {
        return String.format("%02d", hours) + " : " + String.format(
            "%02d",
            minutes
        ) + " : " + String.format("%02d", seconds)
    }


}


