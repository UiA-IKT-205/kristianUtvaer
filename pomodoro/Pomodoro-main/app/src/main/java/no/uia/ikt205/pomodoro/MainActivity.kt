package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var startButton1:Button
    lateinit var startButton2:Button
    lateinit var startButton3:Button
    lateinit var startButton4:Button
    lateinit var coutdownDisplay:TextView

    var timeToCountDownInMs = 300000L
    val timeTicks = 1000L
    var timerstatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton1 = findViewById<Button>(R.id.startCountdownButton1)
        startButton2 = findViewById<Button>(R.id.startCountdownButton2)
        startButton3 = findViewById<Button>(R.id.startCountdownButton3)
        startButton4 = findViewById<Button>(R.id.startCountdownButton4)



        startButton1.setOnClickListener(){
            if(!timerstatus) {
                timeToCountDownInMs = 30 * 60 * 1000
                updateCountDownDisplay(timeToCountDownInMs)
            }
        }
        startButton2.setOnClickListener(){
            if(!timerstatus) {
                timeToCountDownInMs = 60 * 60 * 1000
                updateCountDownDisplay(timeToCountDownInMs)
            }
        }
        startButton3.setOnClickListener(){
            if(!timerstatus) {
                timeToCountDownInMs = 90 * 60 * 1000
                updateCountDownDisplay(timeToCountDownInMs)
            }
        }
        startButton4.setOnClickListener(){
            if(!timerstatus) {
                timeToCountDownInMs = 120 * 60 * 1000
                updateCountDownDisplay(timeToCountDownInMs)
            }
        }

        startButton.setOnClickListener(){
            if(!timerstatus) {
                startCountDown(it)
                timerstatus = true
            }
        }

       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }

    fun startCountDown(v: View){

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                timerstatus = false
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }
}