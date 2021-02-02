package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime
import android.app.Activity;
import android.widget.*


class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var timerPause:CountDownTimer
    lateinit var startButton:Button
    lateinit var startButton1:Button
    lateinit var startButton2:Button
    lateinit var startButton3:Button
    lateinit var startButton4:Button
    lateinit var coutdownDisplay:TextView
    lateinit var SeekBarSetTime: SeekBar
    lateinit var SeekBarSetPause: SeekBar
    lateinit var EditTextRepetition: EditText
    var timeToCountDownInMs = 300000L
    val timeTicks = 1000L
    var timerstatus = false
    var timeToPauseCheck = false
    var timeToPauseInMs = 0L
    var Repvar = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton1 = findViewById<Button>(R.id.startCountdownButton1)
        startButton2 = findViewById<Button>(R.id.startCountdownButton2)
        startButton3 = findViewById<Button>(R.id.startCountdownButton3)
        startButton4 = findViewById<Button>(R.id.startCountdownButton4)
        SeekBarSetTime = findViewById<SeekBar>(R.id.seekBarSetTime)
        SeekBarSetPause = findViewById<SeekBar>(R.id.seekBarSetPause)
        EditTextRepetition = findViewById<EditText>(R.id.editTextRepetition)


       SeekBarSetTime.setOnSeekBarChangeListener(object :
               SeekBar.OnSeekBarChangeListener {
       override fun onProgressChanged(SeekBarSetTime: SeekBar,
            progress: Int, fromUser: Boolean){
           if(!timerstatus) {
               timeToCountDownInMs = progress * 60 * 1000L
               updateCountDownDisplay(timeToCountDownInMs)
           }
       }
           override fun onStartTrackingTouch(SeekBarSetTime: SeekBar?) {
               Toast.makeText(this@MainActivity, "Set tid", Toast.LENGTH_SHORT).show()
           }
           override fun onStopTrackingTouch(SeekBarSetTime: SeekBar){
               Toast.makeText(this@MainActivity, "Tid satt" , Toast.LENGTH_SHORT).show()
           }
       })

        SeekBarSetPause.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(!timerstatus){
                    timeToPauseInMs = progress * 60 * 1000L
                    updateCountDownDisplay(timeToPauseInMs)
                    if(timeToPauseInMs > 0)
                        timeToPauseCheck = true
                }
            }
            override fun onStartTrackingTouch(SeekBarSetPause: SeekBar?) {
                Toast.makeText(this@MainActivity, "Set pause", Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(SeekBarSetPause: SeekBar){
                Toast.makeText(this@MainActivity, "Pause sat" , Toast.LENGTH_SHORT).show()
            }
        })



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
                Repvar = EditTextRepetition.text.toString().toInt()
                startCountDown(it)
                timerstatus = true
            }
        }

       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }

    fun startCountDown(v: View){

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                timerstatus = false
                if(timeToPauseCheck || Repvar > 0) {
                    Repvar--
                    startPause(v)
                }
                else{
                    Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun startPause(v: View){

        timerPause = object : CountDownTimer(timeToPauseInMs,timeTicks) {
            override fun onFinish() {
                timeToPauseCheck = false
                if(Repvar > 0) {
                    timeToPauseCheck = true
                    startCountDown(v)
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }

        timerPause.start()
    }


    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }
}