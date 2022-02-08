package com.example.timerapp.ViewModel

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.properties.Delegates

class TimerViewModel : ViewModel() {
    var  second by Delegates.notNull<Int>()
    var time:Double = 0.0
    var hours by Delegates.notNull<Int>()
    var minutes by Delegates.notNull<Int>()
    var countIncrement :Int = 1
  var incrementValue = "00"
    //
    var timerTask: TimerTask? = null
    var timerStarted = false
    var timer: Timer? = null



}