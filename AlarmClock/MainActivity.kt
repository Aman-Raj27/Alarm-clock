package com.example.alarmclock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    var alarmTimePicker : TimePicker? = null
    var pendingIntent: PendingIntent? =null
    var alarmManager:AlarmManager? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        alarmTimePicker = findViewById(R.id.timePicker)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
    }

    fun onToggleClicked(view: View) {
        var time:Long
        if((view as ToggleButton).isChecked){
            Toast.makeText(this,"Alarm ON",Toast.LENGTH_SHORT).show()
            var calender = Calendar.getInstance()
            calender[Calendar.HOUR_OF_DAY]=alarmTimePicker!!.currentHour
            calender[Calendar.MINUTE]=alarmTimePicker!!.currentMinute

            val intent = Intent(this,AlarmReciever::class.java)
            pendingIntent = PendingIntent.getBroadcast(this,0,intent, PendingIntent.FLAG_IMMUTABLE)
            time=calender.timeInMillis - calender.timeInMillis % 60000

            if(System.currentTimeMillis()>time){
                time=if(Calendar.AM_PM == 0){
                    time +1000* 60 * 60 * 12
                }
                else{
                    time+1000*60*60*24
                }
            }
            alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP,time,1000,pendingIntent!!)
        }
        else{
            alarmManager!!.cancel(pendingIntent!!)
            Toast.makeText(this,"Alarm OFF",Toast.LENGTH_SHORT).show()
        }
    }
}