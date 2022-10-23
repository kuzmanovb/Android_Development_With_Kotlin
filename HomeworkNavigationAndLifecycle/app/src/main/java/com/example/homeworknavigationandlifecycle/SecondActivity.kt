package com.example.homeworknavigationandlifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworknavigationandlifecycle.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var  binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textFromMainActivity = intent.extras?.get("text")
        binding.titleTextview.text = textFromMainActivity.toString()

        binding.buttonBack.setOnClickListener {
            startActivity(Intent(this, MainActivity:: class.java))
        }
    }
}